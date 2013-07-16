package com.joylee.entity;

public class Emuns {
	public enum newssource {
		csdn(1),
		kr(2);

		private int _value;
		private newssource(int value) {
			_value = value;
		}

		public int value() {
			return _value;
		}
	}
}
