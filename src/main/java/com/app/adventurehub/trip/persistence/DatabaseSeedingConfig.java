package com.app.adventurehub.trip.persistence;

import com.app.adventurehub.trip.domain.service.CategoryService;
import com.app.adventurehub.trip.domain.service.DestinationService;
import com.app.adventurehub.trip.domain.service.SeasonService;
import com.app.adventurehub.user.domain.service.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DatabaseSeedingConfig {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseSeedingConfig.class);

	@Autowired
	private SeasonService seasonService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private DestinationService destinationService;

	@Autowired
	private RoleService roleService;

	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {
		String name = event.getApplicationContext().getId();
		logger.info("Starting Database Seeding Process for {} at {}",
				name,
				new Timestamp(System.currentTimeMillis()));
		seasonService.seed();
		categoryService.seed();
		destinationService.seed();
		roleService.seed();
		logger.info("Finished Database Seeding Process for {} at {}",
				name,
				new Timestamp(System.currentTimeMillis()));

	}
}
