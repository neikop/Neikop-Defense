class MapC extends Map {
    constructor() {
        var arrayMap = [
            [21, 31, 16, 15, 96, 17, 16, 32, 31, 16, 15, 96, 17, 16, 32, 21],
            [31, 15, 51, 44, 66, 51, 41, 17, 15, 51, 42, 66, 51, 51, 17, 32],
            [14, 51, 92, 51, 66, 91, 51, 51, 51, 97, 51, 66, 43, 51, 51, 18],
            [14, 51, 61, 65, 67, 65, 62, 44, 51, 61, 65, 67, 65, 62, 93, 18],
            [14, 42, 66, 51, 66, 51, 66, 11, 13, 66, 51, 66, 51, 66, 51, 18],
            [14, 51, 64, 65, 63, 11, 76, 33, 34, 76, 13, 64, 65, 63, 51, 18],
            [14, 51, 51, 97, 51, 17, 76, 32, 31, 76, 15, 51, 41, 51, 43, 18],
            [14, 44, 61, 65, 65, 65, 63, 17, 15, 64, 65, 65, 65, 62, 51, 18],
            [14, 51, 66, 51, 96, 41, 51, 51, 95, 51, 51, 96, 51, 66, 51, 18],
            [14, 43, 66, 44, 64, 65, 65, 65, 65, 62, 44, 66, 51, 66, 44, 18],
            [14, 51, 66, 51, 81, 51, 51, 43, 51, 66, 51, 66, 91, 66, 51, 18],
            [14, 51, 66, 82, 83, 84, 61, 65, 65, 67, 65, 63, 51, 66, 51, 18],
            [14, 95, 64, 62, 51, 51, 66, 51, 41, 66, 51, 51, 61, 63, 42, 18],
            [14, 51, 51, 64, 65, 65, 63, 44, 51, 64, 65, 65, 63, 51, 51, 18],
            [34, 13, 51, 42, 51, 51, 43, 51, 51, 97, 51, 51, 94, 51, 11, 33],
            [21, 34, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 33, 21]
        ];
        var arrayPoint = [];
        arrayPoint.push([
            new Phaser.Point(4, 9),
            new Phaser.Point(9, 9),
            new Phaser.Point(9, 13),
            new Phaser.Point(12, 13),
            new Phaser.Point(12, 12),
            new Phaser.Point(13, 12),
            new Phaser.Point(13, 7),
            new Phaser.Point(9, 7),
            new Phaser.Point(9, 3),
            new Phaser.Point(13, 3),
            new Phaser.Point(13, 5),
            new Phaser.Point(11, 5),
            new Phaser.Point(11, 1)
        ]);
        arrayPoint.push([
            new Phaser.Point(11, 9),
            new Phaser.Point(11, 11),
            new Phaser.Point(6, 11),
            new Phaser.Point(6, 13),
            new Phaser.Point(3, 13),
            new Phaser.Point(3, 12),
            new Phaser.Point(2, 12),
            new Phaser.Point(2, 7),
            new Phaser.Point(6, 7),
            new Phaser.Point(6, 3),
            new Phaser.Point(2, 3),
            new Phaser.Point(2, 5),
            new Phaser.Point(4, 5),
            new Phaser.Point(4, 1)
        ]);
        var arrayPointFly = [];
        arrayPointFly.push([
            new Phaser.Point(11, 15),
            new Phaser.Point(11, 1)
        ]);
        super(arrayMap, arrayPoint, arrayPointFly);
    }
}
