package lk.ijse.bo.custom.impl;

import lk.ijse.bo.CustomerBo;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.customerDto;
import lk.ijse.dto.tm.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBo {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(customerDto dto) throws SQLException, ClassNotFoundException {
        return CustomerDAO.save(new Customer(dto.getCustomerID(),dto.getCustomerName(),dto.getCustomerAddress(),dto.getCustomerContactNumber()));
    }

    @Override
    public boolean updateCustomer(customerDto dto) throws SQLException, ClassNotFoundException {
        return CustomerDAO.update(new Customer(dto.getCustomerName(),dto.getCustomerContactNumber(),dto.getCustomerAddress(),dto.getCustomerID()));
    }

    /*@Override
    public static List<customerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        return getCustomers();
    }*/

    @Override
    public ArrayList<customerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = CustomerDAO.getAll();
        ArrayList<customerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            customerDtos.add(new customerDto(
                    customer.getCustomerID(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCustomerContactNumber()
            ));
        }
        return customerDtos;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return CustomerDAO.delete(id);
    }
}

