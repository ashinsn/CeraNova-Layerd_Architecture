package lk.ijse.bo.custom.impl;

import lk.ijse.bo.OrderDetailBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.OrderDetailDAO;

public class OrderDetailBoImpl implements OrderDetailBo {
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);


}
