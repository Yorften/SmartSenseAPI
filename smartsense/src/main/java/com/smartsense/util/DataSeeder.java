package com.smartsense.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PreDestroy;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import com.smartsense.model.*;
import com.smartsense.model.enums.DeviceType;
import com.smartsense.model.enums.Status;
import com.smartsense.repository.ZoneRepository;
import com.smartsense.repository.DeviceRepository;
import com.smartsense.repository.RoleRepository;
import com.smartsense.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class DataSeeder {

	private RoleRepository roleRepository;
	private UserRepository userRepository;
	private ZoneRepository albumRepository;
	private DeviceRepository musicRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private MongoTemplate mongoTemplate;

	public void seedDatabase(int max) {
		Faker faker = new Faker(new Locale("en-US"));

		LocalDateTime start = LocalDateTime.of(2024, 10, 1, 0, 0, 0, 0);
		LocalDateTime end = LocalDateTime.now();

		Role role1 = Role.builder().name("ROLE_ADMIN").build();
		Role role2 = Role.builder().name("ROLE_USER").build();
		roleRepository.saveAll(Arrays.asList(role1, role2));

		Instant startInstant = Instant.now();
		List<Zone> zones = IntStream.range(0, max).mapToObj(i -> Zone.builder()
				.name(faker.lorem().word())
				.type(faker.lorem().word())
				.location(faker.address().fullAddress())
				.build()).collect(Collectors.toList());
		albumRepository.saveAll(zones);

		CompletableFuture<Void> usersFuture = CompletableFuture.runAsync(() -> {
			log.info("Seeding users started...");

			Role adminRole = roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new RuntimeException("Admin role not found"));
			Role userRole = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("User role not found"));

			List<User> users = IntStream.range(0, max).parallel() // Parallel stream to reduce time taken in
																	// hashing the passwords
					.mapToObj(i -> User.builder()
							.email(faker.internet().emailAddress())
							.username(faker.name().username())
							.password(passwordEncoder.encode("password")) // Expensive
																			// operation
							.roles(i == 0 ? new HashSet<>(Arrays.asList(adminRole))
									: new HashSet<>(Arrays.asList(userRole))) // First user is admin
							.build())
					.collect(Collectors.toList());
			userRepository.saveAll(users);

			log.info("Seeding users completed");
		});

		CompletableFuture<Void> deviceFuture = CompletableFuture.runAsync(() -> {
			log.info("Seeding devices started...");
			List<Device> devices = IntStream.range(0, max)
					.mapToObj(i -> Device.builder()
							.name(faker.lorem().word())
							.type(i == 0 ? DeviceType.TEMPERATURE : DeviceType.HUMIDITY)
							.status(i == 0 ? Status.ACTIVE : Status.INACTIVE)
							.lastCommunication(
									faker.date().between(
											Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
											Date.from(end.atZone(ZoneId.systemDefault()).toInstant()))
											.toInstant()
											.atZone(ZoneId.systemDefault())
											.toLocalDateTime())
							.build())
					.collect(Collectors.toList());
			musicRepository.saveAll(devices);
			log.info("Seeding devices completed");
		});

		CompletableFuture.allOf(usersFuture, deviceFuture).join(); // Ensures both tasks are completed

		long timeTaken = (Instant.now().toEpochMilli() - startInstant.toEpochMilli()) / 1000;

		log.info("Seeding complete : time taken " + timeTaken + " s");

	}

	@PreDestroy
	public void cleanup() {
		mongoTemplate.dropCollection(Device.class);
		mongoTemplate.dropCollection(Zone.class);
		mongoTemplate.dropCollection(User.class);
		mongoTemplate.dropCollection(Role.class);
	}

}
