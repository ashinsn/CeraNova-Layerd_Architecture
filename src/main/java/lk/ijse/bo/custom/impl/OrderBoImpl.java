package lk.ijse.bo.custom.impl;

import lk.ijse.bo.OrderBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dto.orderDto;
import lk.ijse.dto.tm.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBoImpl implements OrderBo {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public boolean saveOrder(orderDto dto) throws SQLException, ClassNotFoundException {
        return OrderDAO.save(new Order(dto.getOrderID(),dto.getOrderDate(),dto.getOrderDescription()));
    }
    @Override
    public boolean updateOrder(orderDto dto) throws SQLException, ClassNotFoundException {
        return OrderDAO.update(new Order(dto.getOrderDate(),dto.getOrderDescription(),dto.getOrderID()));
    }
    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return OrderDAO.delete(id);
    }
    @Override
    public ArrayList<orderDto> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = OrderDAO.getAll();
        ArrayList<orderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
           orderDtos.add(new orderDto(
                order.getOrderID(),
                order.getOrderDate(),
                order.getOrderDescription(),
                order.getDeliveryID()
            ));
        }
        return orderDtos;
    }
}
