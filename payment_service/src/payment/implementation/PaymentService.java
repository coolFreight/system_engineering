package payment.implementation;

import payment.model.Payment;

public interface PaymentService {

    boolean authorizedPayment(Payment payment);
}
