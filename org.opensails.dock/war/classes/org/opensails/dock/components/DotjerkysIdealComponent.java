package org.opensails.dock.components;

public class DotjerkysIdealComponent /* extends BaseComponent*/ {
	/*@Exposed @NotRemembered*/ String requiredProperty;
	/*@Exposed @Remembered*/ String anotherProperty;
	Object complex;
	
	public DotjerkysIdealComponent(String id, String requiredProperty) {
		this.requiredProperty = requiredProperty;
//		super(id);
	}
	
//	@Override
	public void exposition() {
//		super.exposition();
//		expose("complex", someTransform(complex));
	}
	
	/*@Callback*/ void doSomething(String somethingInteresting) {
		// assert id != null;
		assert anotherProperty != null;
		assert requiredProperty == null;
		assert somethingInteresting.equals("really really interesting");
//		renderString("very good");
	}
}
