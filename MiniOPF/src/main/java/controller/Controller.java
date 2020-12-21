package controller;


import lombok.Data;
import model.Model;
import model.entities.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class Controller
{

    Model model = Model.getInstance();

    public AbstractUser login(String login, String password){

    }

    public Customer createCustomer(String login, String password){
        Customer customer = Model.createCustomer(login, password);
        return customer;
    }

    public void updateCustomer(BigInteger id, String firstName, String lastName, String password, String number, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
        Model.updateCustomer(id, firstName, lastName, password, number, addres, balance, servicesIds, ordersIds);
    }

    public void deleteCustomer(BigInteger id){
        Model.deleteCustomer(id);
    }

    public Employee createEmployee(String login, String password){

    }

    public void startOrder(BigInteger employeeId, BigInteger orderId){

    }

    public void suspendOrder(BigInteger employeeId, BigInteger orderId){

    }

    public void restoreOrder(BigInteger employeeId, BigInteger orderId) {

    }

    public void cancelOrder (BigInteger employeeId, BigInteger orderId){

    }

    public void completeOrder (BigInteger employeeId, BigInteger orderId)
    {

    }

    public void topUpBalance(Float amountOfMoney){

    }

    public void topDownBalance(Float amountOfMoney)
    {

    }

    public List<Service> getCustomerConnectedServices(BigInteger customerId){

    }

    public List<Service> getCustomerNotFinishedOrders(BigInteger customerId){

    }

    public Order createNewOrder(BigInteger customerId, BigInteger specId, boolean isForced){

    }

    public Order createSuspendOrder(BigInteger customerId, BigInteger serviceId, boolean isForced){

    }

    public Order createRestoreOrder(BigInteger customerId, BigInteger serviceId, boolean isForced){

    }

    public Order createDisconnectOrder(BigInteger customerId, BigInteger serviceId, boolean isForced){

    }

    public Specification createSpecification(float price, String description, BigInteger districtId){

    }

    public District createDistrict(BigInteger id, String name, List<BigInteger> childrenIds, BigInteger parentId){

    }

    public  List<Order> getOrdersOfEmployeesOnVacation(){

    }

    public  void goOnVacation(BigInteger employeeId){

    }

    public  void returnFromVacation(BigInteger employeeId){

    }

    public void retireEmployee(BigInteger employeeId){

    }

    public void subscribeToWork(BigInteger employeeId){

    }

    public void unsubscribeFromWork(BigInteger employeeId){

    }

}
