package com.commercetools.sync.commons.utils;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.ResourceIdentifier;
import org.junit.Test;

import java.util.UUID;

import static com.commercetools.sync.commons.utils.ResourceIdentifierUtils.toResourceIdentifierIfNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourceIdentifierUtilsTest {

    @Test
    public void toResourceIdentifierIfNotNull_WithNullResource_ShouldReturnNull() {
        assertThat(toResourceIdentifierIfNotNull(null)).isNull();
    }

    @Test
    public void toResourceIdentifierIfNotNull_WithNonNullResource_ShouldReturnCorrectResourceIdentifier() {
        final Category category = mock(Category.class);
        when(category.getId()).thenReturn(UUID.randomUUID().toString());
        when(category.toResourceIdentifier()).thenCallRealMethod();
        when(category.toReference()).thenCallRealMethod();

        final ResourceIdentifier<Category> categoryResourceIdentifier = toResourceIdentifierIfNotNull(category);

        assertThat(categoryResourceIdentifier).isNotNull();
        assertThat(categoryResourceIdentifier.getId()).isEqualTo(category.getId());
        assertThat(categoryResourceIdentifier.getTypeId()).isEqualTo(Category.resourceTypeId());
    }



}
