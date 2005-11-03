package org.opensails.sails.template.viento;

import junit.framework.TestCase;

import org.opensails.sails.helper.oem.HelperResolver;
import org.opensails.sails.oem.ClasspathResourceResolver;
import org.opensails.sails.oem.SailsEventFixture;
import org.opensails.sails.util.ClassHelper;
import org.opensails.sails.util.ComponentPackage;

public class VientoTemplateRendererTest extends TestCase {
	// TODO: Remove the extensive knowledge of the builtin package, helper setup
	public void testRenderStringVientoBinding() {
		VientoTemplateRenderer renderer = new VientoTemplateRenderer(new ClasspathResourceResolver(ClassHelper.getPackageDirectory(VientoTemplateRendererTest.class)));
		VientoBinding vientoBinding = new VientoBinding();
		HelperResolver resolver = new HelperResolver(SailsEventFixture.actionGet());
		resolver.push(new ComponentPackage(ClassHelper.getPackage(HelperResolver.class), "Helper"));
		vientoBinding.mixin(resolver);
		renderer.render("VientoTemplateRendererTest", vientoBinding);
	}
}