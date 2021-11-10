package rvip;

import java.util.LinkedList;

public class Process {
	private Values value;
	private static LinkedList<Values> list = new LinkedList<Values>();
	private static LinkedList<Values> queue = new LinkedList<Values>();
	private static int size;

	// Создание, добавление нового объекта "Values" в связанный список
	public void addLink() {
		value = new Values();
		list.add(value);
	}

	// Установка для поля запроса в объекте "Values" значение true.
	public void sendRequest(int index) {
		value = list.get(index);
		value.setRequest(true);
	}

	// Проверка отправки запроса
	public boolean checkRequest() {
		size = list.size();
		boolean hasRequest = false;

		for (int i = 0; i < size; i++) {
			value = list.get(i);
			if (value.getRequest()) {
				hasRequest = true;
			}
		}
		return hasRequest;
	}

	// Отправка ответа, установив значение "reply = true" для вызываемого индекса
	public void sendReply(int index) {
		value = list.get(index);
		value.setReply(true);
	}

	// Возвращение значения "true", если для всех ответов установлено значение
	// "true"
	public boolean checkReplys() {
		boolean allTrue = true;
		size = list.size();
		for (int i = 0; i < size; i++) {
			value = list.get(i);
			if (!value.getReply()) {
				allTrue = false;
			}
		}
		return allTrue;
	}

	// Добавление объекта значения в конец очереди
	public void enque(int index) {
		value = list.get(index);
		queue.addLast(value);
	}

	// Удаление первого объекта значений из очереди
	public void deque() {
		if (!queue.isEmpty()) {
			queue.removeFirst();
		}
	}

	// Проверяет, является ли объект значений, соответствующий переданному индексу,
	// первым в очереди
	public boolean checkFirst(int index) {
		boolean reply = false;
		value = list.get(index);
		if (value == queue.getFirst()) {
			reply = true;
		}
		return reply;
	}
}