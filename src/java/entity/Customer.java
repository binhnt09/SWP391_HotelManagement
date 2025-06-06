/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author viet7
 */
public class Customer extends User {

    public Customer() {
        super();
    }

    public Customer(int userId, String firstName, String lastName, String email,
                    String phone, String address, int userRuleId,
                    String createdAt, String updatedAt, String deletedAt,
                    Integer deletedBy, boolean isDeleted) {
        super(userId, firstName, lastName, email, phone, address,
              userRuleId, createdAt, updatedAt, deletedAt, deletedBy, isDeleted);
    }

}
