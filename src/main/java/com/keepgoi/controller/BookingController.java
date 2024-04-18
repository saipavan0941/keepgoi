package com.keepgoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepgoi.service.BookingService;

@RestController
@RequestMapping("/api/v2")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;

	@GetMapping("/getAllAvailRooms")
	public Object getAllAvailableRooms(@RequestBody Object object) {
		return bookingService.getAllAvailableRooms();
	}

	@PostMapping("/bookRoom")
	public Object bookRoom(@RequestBody Object object) {
		return object;
	}

	@PostMapping("/cancelRoom")
	public Object cancelRoom(@RequestBody Object object) {
		return object;
	}
	
	@PostMapping("/bookingHistory")
	public Object bookingsHistory(@RequestBody Object object) {
		return object;
	}
}
