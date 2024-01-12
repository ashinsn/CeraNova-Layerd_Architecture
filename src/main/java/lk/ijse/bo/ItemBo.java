package lk.ijse.bo;

import lk.ijse.dto.customerDto;
import lk.ijse.dto.itemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBo extends SuperBO{
    boolean saveItem(itemDto dto) throws SQLException,ClassNotFoundException;

    boolean updateItem(itemDto dto) throws SQLException,ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException,ClassNotFoundException;
    ArrayList<itemDto> getAllItems() throws SQLException, ClassNotFoundException;

}
