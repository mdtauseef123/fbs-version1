package com.flight_test.flight_booking.service;

import com.flight_test.flight_booking.entity.PaymentStatus;

public interface PaymentService {
    PaymentStatus makePayment(String paymentData, int amount);
}
