package org.opensails.sails.form.html;

import static org.opensails.sails.form.FormMeta.ACTION_PREFIX;
import junit.framework.TestCase;

import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.AdapterAdapter;
import org.opensails.sails.adapter.AdapterResolverFixture;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.util.Quick;

public class AbstractSubmitTest extends TestCase {
	public void testAction() throws Exception {
		ContainerAdapterResolver adapterResolver = AdapterResolverFixture.container(new AdapterAdapter() {
			@Override
			public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
				return "adapted";
			}
		});
		AbstractSubmit submit = new AbstractSubmit("whocares", "name", adapterResolver) {};
		submit.action("myAction", Quick.list("originalValue"));
		assertEquals("<input name=\"" + ACTION_PREFIX + "myAction_adapted\" type=\"whocares\" value=\"\" />", submit.renderThyself());
	}

	public void testRender() {
		AbstractSubmit submit = new AbstractSubmit("whocares", "name", null) {};
		assertEquals("<input name=\"name\" type=\"whocares\" value=\"\" />", submit.renderThyself());

		submit.value("hehe");
		assertEquals("<input name=\"name\" type=\"whocares\" value=\"hehe\" />", submit.renderThyself());

		/*
		 * Here we drop the name that was given and embed the action to execute.
		 * If they want multiple buttons, they must tell us which action this
		 * Submit is bound to. For now, a form can only be posted to one
		 * controller.
		 */
		submit.action("myAction");
		assertEquals("<input name=\"" + ACTION_PREFIX + "myAction\" type=\"whocares\" value=\"hehe\" />", submit.renderThyself());
	}
}
