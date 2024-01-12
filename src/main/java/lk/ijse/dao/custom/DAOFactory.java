package lk.ijse.dao.custom;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}
    public static DAOFactory getDaoFactory(){
        return(daoFactory==null)?daoFactory= new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,DELIVERY,EMPLOYEE,ITEM,MATERIAL,ORDER,ORDERDETAIL,PAYMENT,SUPPLIER,QUERY
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case MATERIAL:
                return new MaterialDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
