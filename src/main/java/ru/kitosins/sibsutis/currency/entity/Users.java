package ru.kitosins.sibsutis.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * Users representation class
 * @author kitosina
 * @version 0.2
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see Table
 * @see Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    /**
     * An id field for DB identification
     * @see Id
     * @see GeneratedValue
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * username field for DB
     * @see Column
     */
    @Column
    private String username;

    /**
     * password field for DB
     * @see Column
     */
    @Column
    private String password;

    /**
     * email field for DB
     * @see Column
     */
    @Column
    private String email;

    /**
     * User role field for using in authorities in Spring security
     * creates and wires with User_role field.
     * user identifies by user_id field in table
     *
     * @see CollectionTable
     * @see Role
     * @see Enumerated
     * @see JoinColumn
     * @see ElementCollection
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}