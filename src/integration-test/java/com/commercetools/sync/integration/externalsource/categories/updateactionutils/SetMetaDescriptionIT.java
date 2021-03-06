package com.commercetools.sync.integration.externalsource.categories.updateactionutils;


import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.updateactions.SetMetaDescription;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedString;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

import static com.commercetools.sync.categories.utils.CategoryUpdateActionUtils.buildSetMetaDescriptionUpdateAction;
import static com.commercetools.sync.integration.commons.utils.CategoryITUtils.deleteAllCategories;
import static com.commercetools.sync.integration.commons.utils.ITUtils.deleteTypes;
import static com.commercetools.sync.integration.commons.utils.SphereClientUtils.CTP_TARGET_CLIENT;
import static org.assertj.core.api.Assertions.assertThat;

public class SetMetaDescriptionIT {
    private static Category oldCategory;

    /**
     * Deletes Categories and Types from the target CTP projects, then it populates it with category test data.
     */
    @BeforeClass
    public static void setup() {
        deleteAllCategories(CTP_TARGET_CLIENT);
        deleteTypes(CTP_TARGET_CLIENT);

        // Create a mock old category in the target project.
        final CategoryDraft oldCategoryDraft = CategoryDraftBuilder
            .of(
                LocalizedString.of(Locale.ENGLISH, "classic furniture"),
                LocalizedString.of(Locale.ENGLISH, "classic-furniture"))
            .metaDescription(
                LocalizedString.of(Locale.ENGLISH, "classic furniture meta desc",
                    Locale.GERMAN, "Klassische Möbel Meta desc"))
            .build();
        oldCategory = CTP_TARGET_CLIENT.execute(CategoryCreateCommand.of(oldCategoryDraft))
                                       .toCompletableFuture()
                                       .join();
    }

    /**
     * Cleans up the target data that was built in this test class.
     */
    @AfterClass
    public static void tearDown() {
        deleteAllCategories(CTP_TARGET_CLIENT);
        deleteTypes(CTP_TARGET_CLIENT);
    }


    @Test
    public void buildSetMetaDescriptionUpdateAction_WithDifferentValues_ShouldBuildUpdateAction() {
        // Prepare new category draft with a different MetaDescription
        final CategoryDraft newCategory = CategoryDraftBuilder.of(
            LocalizedString.of(Locale.ENGLISH, "classic furniture"),
            LocalizedString.of(Locale.ENGLISH, "classic-furniture"))
                                                              .metaDescription(LocalizedString
                                                                  .of(Locale.ENGLISH, "new desc"))
                                                              .build();

        // Build set MetaDescription update action
        final UpdateAction<Category> setMetaDescriptionUpdateAction =
            buildSetMetaDescriptionUpdateAction(oldCategory, newCategory).orElse(null);


        assertThat(setMetaDescriptionUpdateAction).isNotNull();
        assertThat(setMetaDescriptionUpdateAction.getAction()).isEqualTo("setMetaDescription");
        assertThat(((SetMetaDescription) setMetaDescriptionUpdateAction).getMetaDescription())
            .isEqualTo(newCategory.getMetaDescription());
    }

    @Test
    public void buildSetMetaDescriptionUpdateAction_WithDifferentLocaleOrder_ShouldNotBuildUpdateAction() {
        // Prepare new category draft with a different order of locales of MetaDescription
        final CategoryDraft newCategory = CategoryDraftBuilder
            .of(
                LocalizedString.of(Locale.ENGLISH, "classic furniture"),
                LocalizedString.of(Locale.ENGLISH, "classic-furniture"))
            .metaDescription(
                LocalizedString.of(Locale.GERMAN, "Klassische Möbel Meta desc",
                    Locale.ENGLISH, "classic furniture meta desc"))
            .build();

        // Build set MetaDescription update action
        final UpdateAction<Category> setMetaDescriptionUpdateAction =
            buildSetMetaDescriptionUpdateAction(oldCategory, newCategory).orElse(null);

        assertThat(setMetaDescriptionUpdateAction).isNull();
    }

    @Test
    public void buildSetMetaDescriptionUpdateAction_WithSameValues_ShouldNotBuildUpdateAction() {
        // Prepare new category draft with a same order of locales of MetaDescription
        final CategoryDraft newCategory = CategoryDraftBuilder
            .of(
                LocalizedString.of(Locale.ENGLISH, "classic furniture"),
                LocalizedString.of(Locale.ENGLISH, "classic-furniture"))
            .metaDescription(
                LocalizedString.of(Locale.ENGLISH, "classic furniture meta desc",
                    Locale.GERMAN, "Klassische Möbel Meta desc"))
            .build();

        // Build set MetaDescription update action
        final UpdateAction<Category> setMetaDescriptionUpdateAction =
            buildSetMetaDescriptionUpdateAction(oldCategory, newCategory).orElse(null);

        assertThat(setMetaDescriptionUpdateAction).isNull();
    }
}
