# O14
MiniOPF by O14, NetCracker
    Приложение, представляющее набор функций для взаимодействия между различными операторами предоставляющими услуги и их клиентами. Сотрудник компании может вести учет и редактирование данных о: заказах, услугах, тарифах на услуги, клиентов, регионах поставления услуг. Клиенту предоставляется возможность отправлять заявки на подключение или отключение услуг и просматривать информацию о своих услугах.

Необходимое ПО:
- JDK 8 (скачать: https://www.oracle.com/ru/java/technologies/javase/javase-jdk8-downloads.html ;
         инструкция по установке: https://www.fandroid.info/ustanovka-jdk-java-development-kit/)
- IntelliJ IDEA (скачать: https://www.jetbrains.com/ru-ru/idea/download/#section=windows ;
         инструкция по установке: https://gb.ru/posts/intellij_idea_setup)
- Apache Maven (скачать:https://maven.apache.org/download.cgi ;
         инструкция по установке: http://barancev.github.io/how-to-install-maven-on-windows/)
- Apache Tomcat (скачать: https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.46/bin/ ;
         инструкция по установке и запуску: http://wmdn.ru/java/installation-apache-tomcat-on-windows-quick-guide/)
- PostgreSQL (скачать: https://www.postgresql.org/download/ ;
         инструкция по установке: https://1cloud.ru/help/windows/ustanovka-postgresql-na-windows-server)
- pgAdmin (скачать: https://www.pgadmin.org/download/ ;
         инструкция по установке: http://nextgis.github.io/webgis_course/1/pgadmin_install.html)

Варианты использования программы:

- В зависимости от источника и места хранения данных:
    1) Файл JSON
       ! По умолчанию приложение запускается в режиме работы хранения данных в формате JSON
         (с тестовыми данными в прилагаемом файле - model.json).
       * Для переключения на этот режим работы с режима работы через базу данных в классе ModelFactory
         необходимо для поля currentModel установить значение "modeljson".

    2) База данных PostgreSQL
       Для переключения на этот режим работы с режима работы через JSON необходимо:
       * В классе ModelFactory поменять значение поля currentModel на "modeldb";
       * Создать пользователя базы данных в pgAdmin;
       * Там же создать базу данных выполнив SQL-запрос: 'CREATE DATABASE MiniOPF';
       * В IntelliJ IDEA открыть файл init.sql, выделить все его содежимое и запустить эти
         SQL-запросы на выполнение нажатием комбинации на клавиатуре Ctrl+Shift.

- В зависимости от вида пользовательского интерфейса:
    1) Консольный интерфейс
       Для запуска консольного интерфейса необходимо:
       * В IntelliJ IDEA найти файл класса, с названием 'Main', расположенный в src/main/java/view;
       * Нажать на этот файл правой кнопкой мыши;
       * В появившемся меню выбрать 'Run 'Main.main()''.

    2) Web-интерфейс
       Для запуска web-интерфейса необходимо сперва создать артефакт для Apache Tomcat:
       * В верхнем меню IntelliJ IDEA кликнуть на кнопку Run;
       * В появившемся меню выбрать 'Edit Configurations...';
       * Нажать на плюсик (+) в верхнем левом углу;
       * В появившемся меню выбрать Tomcat Server - Local;
       * В HTTP Port ввести любой свободный порт (можно оставить 8080 по умолчанию, если он свободен);
       * В правом нижнем углу нажать кнопку Fix;
       * В появившемся меню выбрать первую запись;
       * Нажать Ok.
       Далее для запуска самого приложения нужно: 
       * В верхнем меню IntelliJ IDEA кликнуть на кнопку Run;
       * В появившемся меню выбрать 'Run...';
       * В появившемся меню выбрать созданный ранее артефакт Tomcat.
       Для корректной работы web-интерфейса с файлом model.json, его необхожимо скопировать из директории проекта и поместить в папку bin директории вашего Apache Tomcat.
