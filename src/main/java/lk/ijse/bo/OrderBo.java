package lk.ijse.bo;

import lk.ijse.dto.customerDto;
import lk.ijse.dto.orderDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBo extends SuperBO {
    boolean saveOrder(orderDto dto) throws SQLException,ClassNotFoundException;

    boolean updateOrder(orderDto dto) throws SQLException,ClassNotFoundException;
    boolean deleteOrder(String id) throws SQLException,ClassNotFoundException;
    ArrayList<orderDto> getAllOrders() throws SQLException, ClassNotFoundException;

}
