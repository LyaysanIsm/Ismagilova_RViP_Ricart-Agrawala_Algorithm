package rvip;

import java.util.NoSuchElementException;

class Threading implements Runnable {
	private Thread t;
	private String threadName;
	private static int count = 0;
	private int num;
	private int runTime;
	private Process test = new Process();

	Threading(String name) {
		threadName = name;
		System.out.println("Создание " + threadName);
		count++;
		num = count - 1;
		runTime = count * 10;
		test.addLink();
	}

	public void run() {
		System.out.println("Выполнение " + threadName);
		try {
			for (int i = runTime; i > 0; i--) {
				System.out.println("Поток: " + threadName + ", " + i);
				Thread.sleep(50);

				// Проверка запросов
				if (test.checkRequest()) {
					// Отправка ответа для текущего потока
					test.sendReply(num);
				}
			}
		} catch (Exception e) {
			System.out.println("Поток " + threadName + " прерван");
		}

		// Отправка запроса после завершения задачи
		test.sendRequest(num);

		// Добавление текущего потока в конец очереди
		test.enque(num);

		// Ожидание получения ответов, проверка наличия новых запросов
		while (!test.checkReplys()) {
			if (test.checkRequest()) {
				test.sendReply(num);
			}
		}

		// Ожидание установки первого потока в очереди
		try {
			while (!test.checkFirst(num)) {
				System.out.println("Ожидание");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchElementException e) {
			test.enque(num);
		}
		enterCS();

		// Покидание очереди
		try {
			test.deque();
		} catch (NoSuchElementException e) {
			System.out.println("Очередь пустая");
		}
		test.sendReply(num);

		// Завершение потока
		System.out.println("Поток " + threadName + " завершился");
	}

	// Вызов функции запуска
	public void start() {
		System.out.println("Запуск " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	// Модели, входящие в критическую область
	public void enterCS() {
		try {
			System.out.println("Поток " + threadName + " вошел в кристическую область");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}