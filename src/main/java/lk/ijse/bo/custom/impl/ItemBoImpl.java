package lk.ijse.bo.custom.impl;

import lk.ijse.bo.ItemBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.itemDto;
import lk.ijse.dto.tm.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBo {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveItem(itemDto dto) throws SQLException, ClassNotFoundException {
    return ItemDAO.save(new Item(dto.getItemID(),dto.getItemName(),dto.getItemType(),dto.getItemPrice(),dto.getItemSize(),dto.getQuantityOfItem()));
    }
     @Override
     public boolean updateItem(itemDto dto) throws SQLException, ClassNotFoundException {
     return ItemDAO.update(new Item(dto.getItemName(),dto.getItemType(),dto.getItemPrice(),dto.getItemSize(),dto.getQuantityOfItem(),dto.getItemID()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return ItemDAO.delete(id);
    }
    @Override
     public ArrayList<itemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = ItemDAO.getAll();
       ArrayList<itemDto> itemDtos = new ArrayList<>();

          for (Item item : items) {
           itemDtos.add(new itemDto(
                   item.getItemID(),
                   item.getItemName(),
                   item.getItemType(),
                   item.getUnitPrice(),
                   item.getItemSize(),
                   item.getQuantityOfItem()
          ));
             }
               return itemDtos;
         }

          }
