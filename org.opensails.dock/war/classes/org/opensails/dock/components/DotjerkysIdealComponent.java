package org.opensails.dock.components;

public class DotjerkysIdealComponent /*extends BaseComponent*/ {
	String requiredProperty;
	/*@Remembered*/ String anotherProperty = "default";
	Object complex;
	
	public DotjerkysIdealComponent(String id, String requiredProperty) {
//		super(id);
		this.requiredProperty = requiredProperty;
	}
	
//	@Override
	public void exposition() {
//		super.exposition();
//		expose("complex", someTransform(complex));
	}
	
	/*@Callback*/ void doSomething(String somethingInteresting) {
//		assert id != null;
		assert anotherProperty != null;
		assert requiredProperty == null;
		assert somethingInteresting.equals("really really interesting");
//		renderString("very good");
	}
}
