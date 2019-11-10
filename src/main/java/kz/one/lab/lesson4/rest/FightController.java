package kz.one.lab.lesson4.rest;

import io.swagger.annotations.ApiOperation;
import kz.one.lab.lesson4.entity.Arena;
import kz.one.lab.lesson4.entity.Fighter;
import kz.one.lab.lesson4.services.CheckingDopingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("onelab")
@Slf4j
public class FightController {
    // Адрес ednpoint'a=onelab+fight = localhost:port/onelab/fight
    @Autowired
    Arena arena;

    @Autowired
    CheckingDopingService dopingService;

    @ApiOperation("Add Fighter")
    @PutMapping(value = "fight", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Arena> helloOneLab(@RequestBody Fighter fighter)
    {

        arena.getFighters().add(fighter);
        log.info("test"+arena.getFighters());
        return new ResponseEntity(arena, HttpStatus.OK);
    }
    @ApiOperation("Check All Fighters")
    @PostMapping(value = "check", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fighter> check(){
        List<Fighter> fighters =arena.getFighters();
        //вывели имена всех бойсов в арене, при этом не меняя нижнюю строкуx
        for(Fighter fighter : fighters){
            log.info(fighter.getName());
        }
        log.info("Our warriors: "+arena.getFighters());
        // выбросить в лузеров из бойцов всех тех, кто не прошел допинг контроль
        List<Fighter> losers = new ArrayList<>();
        for(Fighter fighter : fighters){
            if(dopingService.checkFigher(fighter) == false){
                losers.add(fighter);
            }
        }
        return losers;
    }


}
