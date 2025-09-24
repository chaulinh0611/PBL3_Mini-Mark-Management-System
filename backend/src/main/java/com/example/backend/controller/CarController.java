package com.example.backend.controller;
import com.example.backend.model.Car;
import com.example.backend.service.CarService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins = "*")


public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/{id}")
    public Car getCar(@PathVariable Long id){
        return carService.getCar(id);
    }
    @GetMapping("/all")
    public List<Car> getAllCars(){
        return carService.getAllCars();
    }
    @GetMapping("/byUser/{userId}")
    public List<Car> getCarsByUserId(@PathVariable Long userId){
      
        return carService.getCarsByUserId(userId);
    }
}
