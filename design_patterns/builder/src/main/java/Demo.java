import builders.CarBuilder;
import builders.CarManualBuilder;
import cars.Car;
import cars.Manual;
import director.Director;

public class Demo {
    public static void main(String[] args) {
        Director director = new Director();

        // 조립 레시피 설정
        CarBuilder builder = new CarBuilder();
        director.constructSportsCar(builder);

        // 거대한 생성자 문제를 빌더가 떠안는다.
        Car car = builder.getResult();
        System.out.println("Car built:\n" + car.getCarType());


        CarManualBuilder manualBuilder = new CarManualBuilder();

        // Director may know several building recipes.
        director.constructSportsCar(manualBuilder);
        Manual carManual = manualBuilder.getResult();
        System.out.println("\nCar manual built:\n" + carManual.print());
    }
}
