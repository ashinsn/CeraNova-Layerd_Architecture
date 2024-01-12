package lk.ijse.bo.custom.impl;

import lk.ijse.bo.DeliveryBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.dto.deliveryDto;
import lk.ijse.dto.tm.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryBoImpl implements DeliveryBo {
    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DELIVERY);

    @Override
    public boolean saveDelivery(deliveryDto dto) throws SQLException, ClassNotFoundException {
        return DeliveryDAO.save(new Delivery(dto.getDeliveryID(),dto.getDeliveryDate(),dto.getDeliveryDescription(),dto.getDeliveryCost()));
    }
    @Override
    public boolean updateDelivery(deliveryDto dto) throws SQLException, ClassNotFoundException {
        return DeliveryDAO.update(new Delivery(dto.getDeliveryDate(),dto.getDeliveryDescription(),dto.getDeliveryCost(),dto.getDeliveryID()));
    }
    @Override
    public ArrayList<deliveryDto> getAllDelivery() throws SQLException, ClassNotFoundException{
        ArrayList<Delivery> deliveries = DeliveryDAO.getAll();
        ArrayList<deliveryDto> deliveryDtos = new ArrayList<>();

        for (Delivery delivery : deliveries) {
           deliveryDtos.add(new deliveryDto(
                    delivery.getDeliveryId(),
                    delivery.getDeliveryDate(),
                    delivery.getDeliveryDescription(),
                    delivery.getDeliveryCost(),
                    delivery.getCustomerID()
            ));
        }
        return deliveryDtos;
    }
    @Override
    public boolean deleteDelivery(String id) throws SQLException, ClassNotFoundException {
        return DeliveryDAO.delete(id);
    }

   /* @Override
    public static List<String> getDeliveryID() throws SQLException {
        return getDeliveryID();
    }*/

}
