package lk.ijse.bo;

import lk.ijse.dto.customerDto;
import lk.ijse.dto.deliveryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryBo extends SuperBO {
    boolean saveDelivery(deliveryDto dto) throws SQLException,ClassNotFoundException;

    boolean updateDelivery(deliveryDto dto) throws SQLException,ClassNotFoundException;
    boolean deleteDelivery(String id) throws SQLException,ClassNotFoundException;
    ArrayList<customerDto> getAllDelivery() throws SQLException, ClassNotFoundException;

}
