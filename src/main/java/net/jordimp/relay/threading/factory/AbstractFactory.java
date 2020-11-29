package net.jordimp.relay.threading.factory;

public interface AbstractFactory<T> {
	T create(Integer id);
}
