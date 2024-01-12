package lk.ijse.bo;

import lk.ijse.dto.orderDto;
import lk.ijse.dto.paymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBo extends SuperBO {
    boolean savePayment(paymentDto dto) throws SQLException,ClassNotFoundException;

    boolean updatePayment(paymentDto dto) throws SQLException,ClassNotFoundException;
    boolean deletePayment(String id) throws SQLException,ClassNotFoundException;
    ArrayList<paymentDto> getAllPayments() throws SQLException, ClassNotFoundException;
}
