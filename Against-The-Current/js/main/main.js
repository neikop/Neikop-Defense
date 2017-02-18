var Dakra = {};
Dakra.configs = {
    MAP: 1,
    UNIT: 40,
    PLACE: 51
};

Dakra.configs.screenSize = new Phaser.Point(16, 16);

window.onload = function() {
    Dakra.game = new Phaser.Game(
        (Dakra.configs.screenSize.x + 9) * Dakra.configs.UNIT,
        (Dakra.configs.screenSize.y + 0) * Dakra.configs.UNIT,
        Phaser.AUTO, '', {
            preload: preload,
            create: create,
            update: update,
            render: render
        }, false, false);
}

// preparations before game starts
function preload() {
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

    Dakra.game.load.image('button-play', 'Assets/resource/button/play.png');
    Dakra.game.load.image('button-stop', 'Assets/resource/button/stop.png');
    Dakra.game.load.image('button-restart', 'Assets/resource/button/restart.png');
    Dakra.game.load.image('button-next', 'Assets/resource/button/next.png');
    Dakra.game.load.image('background-hided', 'Assets/background-hided.png');
}

// initialize the game
function create() {

    Dakra.game.physics.startSystem(Phaser.Physics.ARCADE);
    Dakra.game.stage.backgroundColor = "#164AE3";
    Dakra.game.add.sprite(0, 0, 'background-hided');

    Dakra.mapGroup = Dakra.game.add.group();
    Dakra.enemyGroup = Dakra.game.add.physicsGroup();
    Dakra.towerHolderGroup = Dakra.game.add.group();
    Dakra.towerGroup = Dakra.game.add.physicsGroup();
    Dakra.bulletBounceGroup = Dakra.game.add.physicsGroup();
    createBackground();
    createBullets();
    Dakra.STAGE = 1;

    createMap();
    createButtons();

    this.timeRespawnEnemy = 2000;
}

function createBackground() {
    Dakra.backGroup = Dakra.game.add.group();

    Dakra.background = Dakra.backGroup.create(0, 0, 'background-hided');
    Dakra.background.inputEnabled = true;
    Dakra.background.kill();

    Dakra.enemyInfo = new EnemyInfo();
}

function createBullets() {
    Dakra.towerBulletGroup = Dakra.game.add.physicsGroup();
    for (var i = 0; i < 100; i++) new Bullet();
}

function createButtons() {
    Dakra.game.add.button(Dakra.MAP.width * Dakra.configs.UNIT, 500, 'button-stop', onStopClick, this);
    Dakra.game.add.button(Dakra.MAP.width * Dakra.configs.UNIT + 54, 500, 'button-restart', onRestartClick, this);
    Dakra.game.add.button(Dakra.MAP.width * Dakra.configs.UNIT + 2, 555, 'button-next', onNextClick, this);
}

function onStopClick() {
    Dakra.LIFE = 0;
    Dakra.enemyInfo.stop();
}

function onRestartClick() {
    createMap();
}

function onNextClick() {
    Dakra.STAGE++;
    createMap();
}

function createMap() {
    var map = (Dakra.STAGE - 1) % 3 + 1;
    Dakra.MAP = map == 1 ? new MapA() :
        map == 2 ? new MapB() :
        map == 3 ? new MapC() : 0;
    if (map == 1) Dakra.MONEY = 650;
    if (map == 2) Dakra.MONEY = 800;
    if (map == 3) Dakra.MONEY = 1000;
    Dakra.LIFE = 5;
    Dakra.KILL = 0;
    Dakra.enemyInfo.stop();
    createTowers();
    createEnemies();
}

function createTowers() {
    clearGroup(Dakra.towerGroup);
    clearGroup(Dakra.towerHolderGroup);
    Dakra.towers = [];
    for (var i = 0; i < 2; i++) {
        Dakra.towers.push(new TowerA());
        Dakra.towers.push(new TowerB());
        Dakra.towers.push(new TowerC());
        Dakra.towers.push(new TowerD());
    }
}

function createEnemies() {
    clearGroup(Dakra.enemyGroup);
    Dakra.enemies = [];
}

// update game state each frame
function update() {
    goCursor(false);

    if (Dakra.LIFE <= 0) {
        Dakra.towers.forEach(function(tower) {
            tower.stop();
        });
        Dakra.enemies.forEach(function(enemy) {
            enemy.stop();
        });
        return;
    }

    Dakra.towers.forEach(function(tower) {
        if (tower.sprite.alive) tower.update();
    });
    Dakra.enemies.forEach(function(enemy) {
        if (enemy.sprite.alive) enemy.update();
    });
    if (Dakra.enemyInfo.sprite.alive) Dakra.enemyInfo.update();

    if (this.lastEnemyRespawnAt === undefined) this.lastEnemyRespawnAt = 0;
    if (Dakra.game.time.now - this.lastEnemyRespawnAt >= this.timeRespawnEnemy) {
        this.lastEnemyRespawnAt = Dakra.game.time.now;
        var x = Math.random() * 5;
        Dakra.enemies.push(
            x < 1 ? new EnemyA() :
            x < 2 ? new EnemyB() :
            x < 3 ? new EnemyC() :
            x < 4 ? new EnemyD() :
            x < 5 ? new EnemyE() : 0);
        Dakra.enemies.push(new EnemyFly());
    }

    Dakra.game.physics.arcade.overlap(Dakra.towerBulletGroup, Dakra.enemyGroup, onBulletHitActor);
}

function onBulletHitActor(bulletSprite, enemySprite) {
    if (bulletSprite.father.tower.TYPE == 4 & bulletSprite.father.tower.LEVEL >= 2) {
        Dakra.enemies.forEach(function(enemy) {
            var x = bulletSprite.position.x - enemy.sprite.position.x;
            var y = bulletSprite.position.y - enemy.sprite.position.y;
            var distance = Math.sqrt(x * x + y * y);
            if (distance < 100) enemy.beShot(bulletSprite.father);
        });
    } else if (bulletSprite.father.tower.TYPE == 3 & bulletSprite.father.tower.LEVEL >= 2) {
        var min = 1000;
        var index = 0;
        var target;
        for (var i = 0; i < Dakra.enemies.length; i++) {
            if (enemySprite.position == Dakra.enemies[i].sprite.position) continue;
            var x = bulletSprite.position.x - Dakra.enemies[i].sprite.position.x;
            var y = bulletSprite.position.y - Dakra.enemies[i].sprite.position.y;
            var distance = Math.sqrt(x * x + y * y);
            if (distance < min) {
                min = distance;
                target = Dakra.enemies[i];
            }
        }
        new BulletBounce(enemySprite.father, target, bulletSprite.father.tower);
        enemySprite.father.beShot(bulletSprite.father);
    } else enemySprite.father.beShot(bulletSprite.father);

    bulletSprite.kill();
}

function goCursor(active) {
    if (active)
        if (Dakra.cursor) Dakra.cursor.update();
        else Dakra.cursor = new Cursor(Dakra.MAP);
}

function clearGroup(group) {
    while (group.length > 0) {
        group.remove(group.getFirstDead());
        group.remove(group.getFirstAlive());
    }
}

// before camera render (mostly for debug)
function render() {
    Dakra.game.debug.text('GOLD = ' + Dakra.MONEY,
        Dakra.MAP.width * Dakra.configs.UNIT, 300);
    if (Dakra.LIFE > 0) Dakra.game.debug.text('LIFE = ' + Dakra.LIFE,
        Dakra.MAP.width * Dakra.configs.UNIT, 340);
    else Dakra.game.debug.text('Game Over',
        Dakra.MAP.width * Dakra.configs.UNIT, 340);
    Dakra.game.debug.text('ENEMY = ' + Dakra.enemyGroup.length,
        Dakra.MAP.width * Dakra.configs.UNIT, 380);
    Dakra.game.debug.text('KILL = ' + Dakra.KILL,
        Dakra.MAP.width * Dakra.configs.UNIT, 420);
    Dakra.game.debug.text('STAGE = ' + ((Dakra.STAGE - 1) % 3 + 1),
        Dakra.MAP.width * Dakra.configs.UNIT, 460);

    Dakra.game.debug.text('Price = 200',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        1 * (Dakra.configs.UNIT + 20) - 10);
    Dakra.game.debug.text('Normal tower',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        1 * (Dakra.configs.UNIT + 20) + 10);
    Dakra.game.debug.text('Price = 150',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        2 * (Dakra.configs.UNIT + 20) - 10);
    Dakra.game.debug.text('Slow tower',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        2 * (Dakra.configs.UNIT + 20) + 10);
    Dakra.game.debug.text('Price = 300',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        3 * (Dakra.configs.UNIT + 20) - 10);
    Dakra.game.debug.text('Armor Penetration',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        3 * (Dakra.configs.UNIT + 20) + 10);
    Dakra.game.debug.text('Price = 400',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        4 * (Dakra.configs.UNIT + 20) - 10);
    Dakra.game.debug.text('High DPS - slow speed',
        (Dakra.MAP.width + 3) * Dakra.configs.UNIT,
        4 * (Dakra.configs.UNIT + 20) + 10);
}
