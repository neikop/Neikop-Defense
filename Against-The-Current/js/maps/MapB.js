class MapB {
    constructor() { // 9 x 12
        this.arrayMap = [
            [0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0],
            [2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 0],
            [0, 0, 0, 0, 2, 0, 0, 2, 1, 0, 2, 0],
            [1, 0, 0, 1, 2, 0, 1, 2, 0, 0, 2, 1],
            [0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0],
            [0, 2, 0, 0, 2, 0, 0, 2, 1, 0, 0, 0],
            [0, 2, 1, 0, 2, 1, 0, 2, 0, 0, 0, 1],
            [1, 2, 0, 0, 2, 2, 2, 2, 0, 1, 0, 0],
            [0, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0]
        ];
        this.arrayPoint = [];
        this.arrayPoint.push([
            new Phaser.Point(1, 0),
            new Phaser.Point(1, 4),
            new Phaser.Point(7, 4),
            new Phaser.Point(7, 7),
            new Phaser.Point(1, 7),
            new Phaser.Point(1, 10),
            new Phaser.Point(4, 10),
            new Phaser.Point(4, 1),
            new Phaser.Point(8, 1),
        ]);

        this.width = this.arrayMap.length;
        this.height = this.arrayMap[0].length;

        for (var w = 0; w < this.width; w++) {
            for (var h = 0; h < this.height; h++) {
                var type = this.arrayMap[w][h];
                var g = Math.random() * 4;
                var ground = g < 1 ? 'ground-a.png' : g < 2 ? 'ground-b.png' : g < 3 ? 'ground-c.png' : 'ground-d.png';
                var flower = 'flower.png';
                var road = Math.random() < 0.5 ? 'road-e.png' : 'road-f.png';
                Dakra.game.add.sprite(w * Dakra.configs.UNIT, h * Dakra.configs.UNIT, 'maps',
                    type == 0 ? ground :
                    type == 1 ? flower :
                    type == 2 ? road : null);
            }
        }
        var r = [];
        for (var i = 0; i < this.arrayMap[0].length; i++) r.push(1);
        for (var i = 0; i < 3; i++) this.arrayMap.push(r);
    }
}
