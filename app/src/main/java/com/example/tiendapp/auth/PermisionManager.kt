package com.example.tiendapp.auth
import com.example.tiendapp.model.domain.Role

object PermissionManager {

    fun canCreateOrder(role: Role) =
        role == Role.CLIENTE

    fun canViewAllOrders(role: Role) =
        role == Role.ADMIN || role == Role.VENDEDOR

    fun canAccessOrders(role: Role?): Boolean =
        role in listOf(
            Role.ADMIN,
            Role.CLIENTE,
            Role.REPARTIDOR,
            Role.VENDEDOR
        )

    fun canViewOwnOrders(role: Role?): Boolean =
        role == Role.CLIENTE || role == Role.REPARTIDOR

    fun canViewDeliveryOrders(role: Role) =
        role == Role.REPARTIDOR

    fun canManageDeliveries(role: Role) =
        role == Role.REPARTIDOR || role == Role.ADMIN

    fun canUpdateOrderStatus(role: Role) =
        role == Role.ADMIN || role == Role.VENDEDOR || role == Role.REPARTIDOR

    fun canCreateProduct(role: Role) =
        role == Role.ADMIN || role == Role.VENDEDOR

    fun canViewProducts(role: Role) =
        role != Role.REPARTIDOR

    fun canViewUsers(role: Role) =
        role == Role.ADMIN
}
