package com.commercetools.sync.commons.enums;


import javax.annotation.Nonnull;

/**
 * Represents all error messages across the sync modules.
 */
public enum Error {
    UPDATE_ACTION_NOT_IMPLEMENTED("Update actions for resource: '%s' is not implemented."),
    SET_CUSTOM_TYPE_BUILD_FAILED("Failed to build 'setCustomType' update action on the %s with id '%s'. Reason: %s"),
    REMOVE_CUSTOM_TYPE_BUILD_FAILED("Failed to build 'setCustomType' update action to remove the custom type on the "
      + "%s with id '%s'. Reason: %s"),
    SET_CUSTOM_FIELD_BUILD_FAILED("Failed to build 'setCustomField' update action on "
      + "the custom field with the name '%s' on the %s with id '%s'. Reason: %s"),
    CTP_CATEGORY_UPDATE_FAILED("Failed to update category with id '%s' in CTP project with key '%s. Reason: %s"),
    CTP_CATEGORY_FETCH_FAILED("Failed to fetch category with external id '%s' in CTP project with key '%s."
        + " Reason: %s"),
    CTP_CATEGORY_CREATE_FAILED("Failed to create category with external id '%s' in CTP project with key '%s."
        + " Reason: %s"),
    CTP_CATEGORY_SYNC_FAILED("Failed to sync category with external id '%s' in CTP project with key '%s."
                                   + " Reason: %s");


    private final String description;

    Error(@Nonnull final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
