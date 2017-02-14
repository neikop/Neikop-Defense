var Dakra = {};
Dakra.configs = {
    screenWidth: 440,
    screenHeight: 600,
    UNIT: 40
};

window.onload = function() {
    Dakra.game = new Phaser.Game(
        Dakra.configs.screenWidth + 3 * Dakra.configs.UNIT,
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

    Dakra.game.load.atlasJSONHash('maps', 'Assets/maps.png', 'Assets/maps.json');
    Dakra.game.load.atlasJSONHash('cursors', 'Assets/cursors.png', 'Assets/cursors.json');
    Dakra.game.load.atlasJSONHash('towers', 'Assets/towers.png', 'Assets/towers.json');
    Dakra.game.load.atlasJSONHash('holders', 'Assets/holders.png', 'Assets/holders.json');
    Dakra.game.load.atlasJSONHash('bullets', 'Assets/bullets.png', 'Assets/bullets.json');
}

// initialize the game
var create = function() {
    Dakra.game.physics.startSystem(Phaser.Physics.ARCADE);
    Dakra.game.stage.backgroundColor = "#4488AA";
    Dakra.map = new MapA();

    Dakra.enemyGroup = Dakra.game.add.physicsGroup();
    Dakra.towerHolderGroup = Dakra.game.add.physicsGroup();
    Dakra.towerGroup = Dakra.game.add.physicsGroup();
    Dakra.towerBulletGroup = Dakra.game.add.physicsGroup();

    Dakra.towers = [];
    for (var i = 0; i < 2; i++) {
        Dakra.towers.push(new TowerA());
        Dakra.towers.push(new TowerB());
        Dakra.towers.push(new TowerC());
        Dakra.towers.push(new TowerD());
    }

    Dakra.enemies = [];
    Dakra.lastEnemyRespawnAt = 0;
    Dakra.countDead = 0;

}

// update game state each frame
var update = function() {
    goCursor(false);

    Dakra.towers.forEach(function(tower) {
        tower.update();
    });
    Dakra.enemies.forEach(function(enemy) {
        if (enemy.sprite.alive) enemy.update();
    });

    if (Dakra.game.time.now - Dakra.lastEnemyRespawnAt >= 3000) {
        Dakra.lastEnemyRespawnAt = Dakra.game.time.now;
        Dakra.enemies.push(new EnemyA());
    }

    Dakra.game.physics.arcade.overlap(Dakra.towerBulletGroup, Dakra.enemyGroup, onBulletHitActor);
}

var onBulletHitActor = function(bulletSprite, actorSprite) {
    actorSprite.damage(1);
    bulletSprite.kill();
}

function goCursor(active) {
    if (active)
        if (Dakra.cursor) Dakra.cursor.update();
        else Dakra.cursor = new Cursor(Dakra.map);
}

// before camera render (mostly for debug)
var render = function() {
    Dakra.game.debug.text('Alive: ' + Dakra.enemies.length, 464, 400);
    Dakra.game.debug.text('Dead: ' + Dakra.countDead, 465, 440);
}
