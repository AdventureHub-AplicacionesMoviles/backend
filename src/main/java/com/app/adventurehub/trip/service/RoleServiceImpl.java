package com.app.adventurehub.trip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.adventurehub.user.domain.model.entity.Role;
import com.app.adventurehub.user.domain.persistence.RoleRepository;
import com.app.adventurehub.user.domain.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
	@Autowired
	private final RoleRepository roleRepository;
	private static Integer[] DEFAULT_ID = { 1, 2, 3 };
	private static String[] DEFAULT_ROLE = { "TRAVELER", "AGENCY", "ADMIN" };

	@Override
	public void seed() {
		// TODO Auto-generated method stub
		// MAP DEFAULT_ID AND DEFAULT_ROLE
		for (int i = 0; i < DEFAULT_ID.length; i++) {
			if (!roleRepository.existsById(DEFAULT_ID[i])) {
				System.out.println("RoleServiceImpl: " + DEFAULT_ID[i] + " " + DEFAULT_ROLE[i]);
				roleRepository.save((new Role()).withId(DEFAULT_ID[i]).withName(DEFAULT_ROLE[i]));
			}
		}
	}
}
