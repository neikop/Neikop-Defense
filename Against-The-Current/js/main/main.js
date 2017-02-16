var Dakra = {};
Dakra.configs = {
    MAP: 1,
    UNIT: 40,
    PLACE: 51
};

Dakra.configs.screenSize = new Phaser.Point(16, 16);

window.onload = function() {
    Dakra.game = new Phaser.Game(
        (Dakra.configs.screenSize.x + 3) * Dakra.configs.UNIT,
        (Dakra.configs.screenSize.y + 0) * Dakra.configs.UNIT,
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

    Dakra.game.load.atlasJSONHash('maps', 'Assets/maps.png', 'Assets/maps.json');
    Dakra.game.load.atlasJSONHash('cursors', 'Assets/cursors.png', 'Assets/cursors.json');
    Dakra.game.load.atlasJSONHash('towers', 'Assets/towers.png', 'Assets/towers.json');
    Dakra.game.load.atlasJSONHash('holders', 'Assets/holders.png', 'Assets/holders.json');
    Dakra.game.load.atlasJSONHash('bullets', 'Assets/bullets.png', 'Assets/bullets.json');
    Dakra.game.load.atlasJSONHash('chars', 'Assets/chars.png', 'Assets/chars.json');

    Dakra.game.load.image('button-destroy', 'Assets/resource/button/destroy.png');
    Dakra.game.load.image('button-upgrade', 'Assets/resource/button/upgrade.png');
    Dakra.game.load.image('background-hided', 'Assets/background-hided.png');
}

// initialize the game
var create = function() {
    Dakra.game.physics.startSystem(Phaser.Physics.ARCADE);
    Dakra.game.stage.backgroundColor = "#164AE3";
    Dakra.game.add.sprite(0, 0, 'background-hided');
    Dakra.MAP =
        Dakra.configs.MAP == 1 ? new MapA() :
        Dakra.configs.MAP == 2 ? new MapB() :
        Dakra.configs.MAP == 3 ? new MapC() : 0;
    Dakra.MONEY = 650;
    Dakra.LIFE = 10;
    Dakra.KILL = 0;

    Dakra.enemyGroup = Dakra.game.add.physicsGroup();
    Dakra.towerHolderGroup = Dakra.game.add.group();
    Dakra.towerGroup = Dakra.game.add.physicsGroup();
    Dakra.backGroup = Dakra.game.add.group();
    Dakra.towerBulletGroupA = Dakra.game.add.physicsGroup();
    Dakra.towerBulletGroupB = Dakra.game.add.physicsGroup();
    Dakra.towerBulletGroupC = Dakra.game.add.physicsGroup();
    Dakra.towerBulletGroupD = Dakra.game.add.physicsGroup();

    createTowers();
    createEnemies();
    createBullets();
    createBackground();
    this.timeRespawnEnemy = 2000;
}

var createTowers = function() {
    Dakra.towers = [];
    for (var i = 0; i < 2; i++) {
        Dakra.towers.push(new TowerA());
        Dakra.towers.push(new TowerB());
        Dakra.towers.push(new TowerC());
        Dakra.towers.push(new TowerD());
    }
}

var createEnemies = function() {
    Dakra.enemies = [];
}

var createBullets = function() {
    for (var i = 0; i < 100; i++) {
        new BulletA();
        new BulletB();
        new BulletC();
        new BulletD();
    }
}

var createBackground = function() {
    Dakra.background = Dakra.backGroup.create(0, 0, 'background-hided');
    Dakra.background.inputEnabled = true;
    Dakra.background.kill();
}

// update game state each frame
var update = function() {
    goCursor(false);

    Dakra.towers.forEach(function(tower) {
        if (tower.sprite.alive) tower.update();
    });
    Dakra.enemies.forEach(function(enemy) {
        if (enemy.sprite.alive) enemy.update();
    });

    if (this.lastEnemyRespawnAt === undefined) this.lastEnemyRespawnAt = 0;
    if (Dakra.game.time.now - this.lastEnemyRespawnAt >= this.timeRespawnEnemy) {
        this.lastEnemyRespawnAt = Dakra.game.time.now;
        var x = Math.random() * 5;
        Dakra.enemies.push(
            x < 1 ? new EnemyA() :
            x < 2 ? new EnemyB() :
            x < 3 ? new EnemyC() :
            x < 4 ? new EnemyD() : new EnemyE);
    }

    Dakra.game.physics.arcade.overlap(Dakra.towerBulletGroupA, Dakra.enemyGroup, onBulletHitActor);
    Dakra.game.physics.arcade.overlap(Dakra.towerBulletGroupB, Dakra.enemyGroup, onBulletHitActor);
    Dakra.game.physics.arcade.overlap(Dakra.towerBulletGroupC, Dakra.enemyGroup, onBulletHitActor);
    Dakra.game.physics.arcade.overlap(Dakra.towerBulletGroupD, Dakra.enemyGroup, onBulletHitActor);
}

var onBulletHitActor = function(bulletSprite, enemySprite) {
    enemySprite.father.beShot(bulletSprite.father);
    bulletSprite.kill();
}

function goCursor(active) {
    if (active)
        if (Dakra.cursor) Dakra.cursor.update();
        else Dakra.cursor = new Cursor(Dakra.MAP);
}

// before camera render (mostly for debug)
var render = function() {
    Dakra.game.debug.text('M = ' + Dakra.MONEY,
        (Dakra.MAP.width + 0.5) * Dakra.configs.UNIT, 320);
    if (Dakra.LIFE > 0) Dakra.game.debug.text('L = ' + Dakra.LIFE,
        (Dakra.MAP.width + 0.5) * Dakra.configs.UNIT, 360);
    else Dakra.game.debug.text('Game Over',
        (Dakra.MAP.width + 0.5) * Dakra.configs.UNIT, 360);
    Dakra.game.debug.text('E = ' + Dakra.enemyGroup.length,
        (Dakra.MAP.width + 0.5) * Dakra.configs.UNIT, 400);
    Dakra.game.debug.text('K = ' + Dakra.KILL,
        (Dakra.MAP.width + 0.5) * Dakra.configs.UNIT, 440);
}
