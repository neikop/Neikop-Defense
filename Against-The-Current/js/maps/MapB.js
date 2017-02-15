class MapB extends Map {
    constructor() {
        var arrayMap = [
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
        var arrayPoint = [];
        arrayPoint.push([
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
        super(arrayMap, arrayPoint);
    }
}
