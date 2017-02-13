var Dakra = {};
Dakra.configs = {
    screenWidth: 440,
    screenHeight: 600
};

window.onload = function() {
    Dakra.game = new Phaser.Game(
        Dakra.configs.screenWidth + 120,
        Dakra.configs.screenHeight,
        Phaser.AUTO, '', {
            preload: preload,
            create: create,
            update: update,
            render: render
        }, false, false);
}

// preparations before game starts
var preload = function() {
    Dakra.game.scale.pageAlignHorizontally = true;
    Dakra.game.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;

    Dakra.game.time.advancedTiming = true;

    Dakra.game.load.atlasJSONHash('cursors', 'Assets/cursors.png', 'Assets/cursors.json');
    Dakra.game.load.atlasJSONHash('towers', 'Assets/towers.png', 'Assets/towers.json');

    Dakra.game.load.image('ground-a', 'Assets/Images/map-maker/ground-a.png');
    Dakra.game.load.image('ground-b', 'Assets/Images/map-maker/ground-b.png');
    Dakra.game.load.image('ground-c', 'Assets/Images/map-maker/ground-c.png');
    Dakra.game.load.image('ground-d', 'Assets/Images/map-maker/ground-d.png');
    Dakra.game.load.image('flower', 'Assets/Images/map-maker/flower.png');
    Dakra.game.load.image('road-e', 'Assets/Images/map-maker/road-e.png');
    Dakra.game.load.image('road-f', 'Assets/Images/map-maker/road-f.png');
}

// initialize the game
var create = function() {
    Dakra.game.physics.startSystem(Phaser.Physics.ARCADE);
    Dakra.keyboard = Dakra.game.input.keyboard;
    Dakra.game.input.mouse.capture = true;
    Dakra.game.stage.backgroundColor = "#4488AA";

    Dakra.map = new MapA();

    // Dakra.cursor = new Cursor(Dakra.map);

    Dakra.towers = [];
    for (var i = 0; i < 2; i++) {
        Dakra.towers.push(new TowerA(Dakra.map));
        Dakra.towers.push(new TowerB(Dakra.map));
        Dakra.towers.push(new TowerC(Dakra.map));
        Dakra.towers.push(new TowerD(Dakra.map));
    }
}

// update game state each frame
var update = function() {
    // Dakra.cursor.update();
    Dakra.towers.forEach(function(tower) {
        tower.update();
    });
}

// before camera render (mostly for debug)
var render = function() {}
