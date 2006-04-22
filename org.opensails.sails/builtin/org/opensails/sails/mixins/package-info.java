/**
 * Contains built-in Viento type-specific mixins.
 */

@ClassKeyMappings({
	@Mapping(classKeys = { String.class }, value = HtmlMixin.class),
	@Mapping(classKeys = { Throwable.class }, value = ThrowableMixin.class)
})

package org.opensails.sails.mixins;

import org.opensails.spyglass.ClassKeyMappings;
import org.opensails.spyglass.Mapping;