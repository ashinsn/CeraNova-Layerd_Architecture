package lk.ijse.bo.custom.impl;

import lk.ijse.bo.PaymentBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dto.paymentDto;
import lk.ijse.dto.tm.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBoImpl implements PaymentBo {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean savePayment(paymentDto dto) throws SQLException, ClassNotFoundException {
        return PaymentDAO.save(new Payment(dto.getPaymentID(), dto.getPaymentDate(), dto.getPaymentMethod(), dto.getPaymentCost(), dto.getPaymentTime(), dto.getOrderID(), dto.getItemID(), dto.getCustomerID()));
    }

    @Override
    public boolean updatePayment(paymentDto dto) throws SQLException, ClassNotFoundException {
        return PaymentDAO.update(new Payment(dto.getPaymentDate(), dto.getPaymentMethod(), dto.getPaymentCost(), dto.getPaymentTime(), dto.getOrderID(), dto.getItemID(), dto.getCustomerID(), dto.getPaymentID()));
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return PaymentDAO.delete(id);
    }

    @Override
    public ArrayList<paymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = PaymentDAO.getAll();
        ArrayList<paymentDto> paymentDtos = new ArrayList<>();

        for (Payment payment : payments) {
            paymentDtos.add(new paymentDto(
                    payment.getPaymentID(),
                    payment.getPaymentDate(),
                    payment.getPaymentMethod(),
                    payment.getPaymentCost(),
                    payment.getPaymentTime(),
                    payment.getOrderID(),
                    payment.getItemID(),
                    payment.getCustomerID()
            ));
        }
        return paymentDtos;
    }
}
