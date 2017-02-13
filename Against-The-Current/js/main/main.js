var Dakra = {};
Dakra.configs = {
    screenWidth: 440,
    screenHeight: 600
};

window.onload = function() {
    Dakra.game = new Phaser.Game(
        Dakra.configs.screenWidth,
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

    Dakra.game.load.image('road-e', 'Assets/Images/map-maker/road-e.png');
    Dakra.game.load.image('road-f', 'Assets/Images/map-maker/road-f.png');
    Dakra.game.load.image('flower', 'Assets/Images/map-maker/flower.png');
    Dakra.game.load.image('ground-a', 'Assets/Images/map-maker/ground-a.png');
    Dakra.game.load.image('ground-b', 'Assets/Images/map-maker/ground-b.png');
    Dakra.game.load.image('ground-c', 'Assets/Images/map-maker/ground-c.png');
    Dakra.game.load.image('ground-d', 'Assets/Images/map-maker/ground-d.png');
    Dakra.game.load.image('cursor-green', 'Assets/Images/cursor-green.png');
    Dakra.game.load.image('cursor-red', 'Assets/Images/cursor-red.png');

    // Dakra.game.load.atlasJSONHash('assets', 'Assets/assets.png', 'Assets/assets.json');
    // Dakra.game.load.image('background', 'Assets/Map.png');
}

// initialize the game
var create = function() {
    Dakra.game.physics.startSystem(Phaser.Physics.ARCADE);
    Dakra.keyboard = Dakra.game.input.keyboard;

    Dakra.arrayMap = [
        [0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0],
        [0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0],
        [2, 2, 2, 2, 2, 2, 2, 0, 1, 2, 2, 2, 2, 0, 0],
        [1, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 1, 0],
        [0, 0, 1, 0, 1, 0, 2, 0, 0, 2, 1, 0, 2, 0, 1],
        [0, 0, 2, 2, 2, 2, 2, 1, 0, 2, 0, 0, 2, 0, 0],
        [0, 0, 2, 0, 0, 0, 0, 0, 1, 2, 0, 0, 2, 0, 0],
        [1, 0, 2, 0, 0, 1, 0, 0, 0, 2, 0, 1, 2, 0, 0],
        [0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 2, 1, 0],
        [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 1],
        [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0]
    ];
    for (var i = 0; i < 11; i++) {
        for (var j = 0; j < 15; j++) {
            var x = Dakra.arrayMap[i][j];
            var y = Math.floor(Math.random() * 4) + 1;
            var z = Math.floor(Math.random() * 5) + 1;
            Dakra.game.add.sprite(40 * i, 40 * j,
                x == 0 ? y == 1 ? 'ground-a' : y == 2 ? 'ground-b' : y == 3 ? 'ground-c' : 'ground-d' :
                x == 1 ? 'flower' : Math.random() >= 0.5 ? 'road-e' : 'road-f');
        }
    }

    Dakra.cursor = new Cursor(0, 0);
}

// update game state each frame
var update = function() {
    Dakra.cursor.update();
}

// before camera render (mostly for debug)
var render = function() {}
