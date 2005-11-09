package org.opensails.sails.template.viento;

import junit.framework.TestCase;

import org.opensails.sails.Sails;
import org.opensails.sails.oem.ClasspathResourceResolver;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.template.MixinResolver;
import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.ComponentPackage;

public class VientoTemplateRendererTest extends TestCase {
	// TODO: Remove the extensive knowledge of the builtin package, helper setup
	public void testRenderStringVientoBinding() {
		VientoTemplateRenderer renderer = new VientoTemplateRenderer(new ClasspathResourceResolver(ClassHelper.getPackageDirectory(VientoTemplateRendererTest.class)));
		VientoBinding vientoBinding = new VientoBinding();
		MixinResolver resolver = new MixinResolver(SailsEventFixture.actionGet());
		resolver.push(new ComponentPackage(ClassHelper.getPackage(Sails.class) + ".mixins", "Mixin"));
		vientoBinding.mixin(resolver);
		renderer.render("VientoTemplateRendererTest", vientoBinding);
	}
}
