package com.cbsinc.cms.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Roles fixed in pass 33. Guest(0)/Member(1) are not staff; Administrator(2),
 * Shipping(3) and Fulfillment(4) may process orders; only Administrator has
 * full control.
 */
class SiteRoleTest {

    @Test
    void onlyAdministratorIsAdministrator() {
        assertTrue(SiteRole.isAdministrator(SiteRole.ADMINISTRATOR_ROLE_ID));
        assertFalse(SiteRole.isAdministrator(SiteRole.GUEST_ROLE_ID));
        assertFalse(SiteRole.isAdministrator(SiteRole.MEMBER_ROLE_ID));
        assertFalse(SiteRole.isAdministrator(SiteRole.SHIPPING_ROLE_ID));
        assertFalse(SiteRole.isAdministrator(SiteRole.FULFILLMENT_ROLE_ID));
    }

    @Test
    void staffIsAdminOrShippingOrFulfillment() {
        assertTrue(SiteRole.isStaff(SiteRole.ADMINISTRATOR_ROLE_ID));
        assertTrue(SiteRole.isStaff(SiteRole.SHIPPING_ROLE_ID));
        assertTrue(SiteRole.isStaff(SiteRole.FULFILLMENT_ROLE_ID));
        assertFalse(SiteRole.isStaff(SiteRole.GUEST_ROLE_ID));
        assertFalse(SiteRole.isStaff(SiteRole.MEMBER_ROLE_ID));
    }

    @Test
    void canProcessOrdersMatchesStaff() {
        for (long r = -1; r <= 6; r++) {
            org.junit.jupiter.api.Assertions.assertEquals(
                    SiteRole.isStaff(r), SiteRole.canProcessOrders(r),
                    "canProcessOrders must track isStaff for role " + r);
        }
    }

    @Test
    void unknownRolesHaveNoRights() {
        assertFalse(SiteRole.isStaff(5));
        assertFalse(SiteRole.canProcessOrders(99));
        assertFalse(SiteRole.isAdministrator(-1));
    }
}
