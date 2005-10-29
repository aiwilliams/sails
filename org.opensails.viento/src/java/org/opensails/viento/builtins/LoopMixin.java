package org.opensails.viento.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.opensails.viento.Block;

public class LoopMixin {
	public Loop each(Collection target, String temp, Block each) {
		return new Loop(target).each(temp, each);
	}

	public Loop each(Collection target, String temp, String indexTemp, Block each) {
		return new Loop(target).each(temp, indexTemp, each);
	}
	
	public Loop before(Collection target, Object block) {
		return new Loop(target).before(block);
	}
	
	public Loop each(Object[] target, String temp, Block each) {
		return new Loop(target).each(temp, each);
	}
	
	public Loop each(Object[] target, String temp, String indexTemp, Block each) {
		return new Loop(target).each(temp, indexTemp, each);
	}
	
	public Loop before(Object[] target, Object block) {
		return new Loop(target).before(block);
	}
	
	public class Loop {
		private Object[] target;
		private String temp;
		private String indexTemp;
		private Block each;
		private Object before;
		private Object after;
		private Object delimiter;
		private Set<Object> removals = new HashSet<Object>();
		private Map<String, Object> filter;

		public Loop(Collection target) {
			this.target = target.toArray();
		}
		
		public Loop(Object[] target) {
			this.target = target;
		}
		
		public Loop after(Object after) {
			this.after = after;
			return this;
		}

		public Loop delimiter(Object delimiter) {
			this.delimiter = delimiter;
			return this;
		}

		public Loop before(Object before) {
			this.before = before;
			return this;
		}
		
		public Loop each(String temp, Block each) {
			this.temp = temp;
			this.indexTemp = "index";
			this.each = each;
			return this;
		}
		
		public Loop each(String temp, String indexTemp, Block each) {
			this.temp = temp;
			this.indexTemp = indexTemp;
			this.each = each;
			return this;
		}
		
		public Loop sans(Map<String, Object> filter) {
			this.filter = filter;
			return this;
		}
		
		public Loop sans(Collection<Object> objects) {
			removals.addAll(objects);
			return this;
		}
		
		public Loop sans(Object object) {
			removals.add(object);
			return this;
		}

		@Override
		public String toString() {
			Collection<Object> filtered = filterTarget();
			if (filtered.isEmpty())
				return "";
			StringBuilder buffer = new StringBuilder();
			
			if (before != null)
				buffer.append(before);
			
			int index = 1;
			Iterator iterator = filtered.iterator();
			while (iterator.hasNext()) {
				Object item = iterator.next();

				each.put(indexTemp, index++);
				each.put(temp, item);
				buffer.append(each);
				
				if (iterator.hasNext() && delimiter != null)
					buffer.append(delimiter);
			}
			
			if (after != null)
				buffer.append(after);

			return buffer.toString();
		}

		protected Collection<Object> filterTarget() {
			Collection<Object> filtered = new ArrayList<Object>();
			for (Object object : target)
				if (!removals.contains(object) && !matchesFilter(object))
					filtered.add(object);
			return filtered;
		}

		protected boolean matchesFilter(Object item) {
			if (filter == null)
				return false;
			for (Map.Entry<String, Object> entry : filter.entrySet()) {
				try {
					Object value = each.getBinding().call(item, entry.getKey());
					if (entry.getValue() instanceof Collection && ((Collection)entry.getValue()).contains(value) || entry.getValue().equals(value))
						return true;
				} catch (Exception e) {
				}
			}
			return false;
		}
	}
}
