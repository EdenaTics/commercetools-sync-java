package com.commercetools.sync.commons.utils;

import com.commercetools.sync.categories.CategorySyncOptionsBuilder;
import com.commercetools.sync.categories.helpers.CategoryCustomActionBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.updateactions.SetCustomField;
import io.sphere.sdk.categories.commands.updateactions.SetCustomType;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.commands.UpdateAction;
import org.junit.Test;

import java.util.HashMap;

import static com.commercetools.sync.commons.asserts.actions.AssertionsForUpdateActions.assertThat;
import static io.sphere.sdk.models.ResourceIdentifier.ofId;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CategoryCustomUpdateActionUtilsTest {

    @Test
    public void buildTypedSetCustomTypeUpdateAction_WithCategoryResource_ShouldBuildCategoryUpdateAction() {
        final String newCustomTypeId = "key";

        final UpdateAction<Category> updateAction =
            GenericUpdateActionUtils.buildTypedSetCustomTypeUpdateAction(newCustomTypeId, new HashMap<>(),
                mock(Category.class), new CategoryCustomActionBuilder(), null, Category::getId,
                categoryResource -> categoryResource.toReference().getTypeId(), categoryResource -> null,
                CategorySyncOptionsBuilder.of(mock(SphereClient.class)).build()).orElse(null);

        assertThat(updateAction).isInstanceOf(SetCustomType.class);
        assertThat((SetCustomType) updateAction).hasValues("setCustomType", emptyMap(), ofId(newCustomTypeId));
    }

    @Test
    public void buildRemoveCustomTypeAction_WithCategoryResource_ShouldBuildCategoryUpdateAction() {
        final UpdateAction<Category> updateAction =
            new CategoryCustomActionBuilder().buildRemoveCustomTypeAction(null, null);

        assertThat(updateAction).isInstanceOf(SetCustomType.class);
        assertThat((SetCustomType) updateAction).hasValues("setCustomType", null, ofId(null));
    }

    @Test
    public void buildSetCustomFieldAction_WithCategoryResource_ShouldBuildCategoryUpdateAction() {
        final JsonNode customFieldValue = JsonNodeFactory.instance.textNode("foo");
        final String customFieldName = "name";

        final UpdateAction<Category> updateAction = new CategoryCustomActionBuilder()
            .buildSetCustomFieldAction(null, null, customFieldName, customFieldValue);

        assertThat(updateAction).isInstanceOf(SetCustomField.class);
        assertThat((SetCustomField) updateAction).hasValues("setCustomField", customFieldName, customFieldValue);
    }
}
