public class Bus {
	public static byte read(short address) {
		if (Short.toUnsignedInt(address) >= 32768) {
			return EaterEmulator.rom.read((short)(address-0x8000));
		} else if (Short.toUnsignedInt(address) <= 24592 && Short.toUnsignedInt(address) >= 24576) {
			return EaterEmulator.via.read(address);
		} else {
			return EaterEmulator.ram.read(address);
		}
	}
	
	public static void write(short address, byte data) {
		if (Short.toUnsignedInt(address) >= 32768) {
			System.err.println("Can't write to ROM! ("+Integer.toHexString(Short.toUnsignedInt(address)).toUpperCase()+")");
		} else if (Short.toUnsignedInt(address) <= 24592 && Short.toUnsignedInt(address) >= 24576) {
			EaterEmulator.via.write(address, data);
		} else if (Short.toUnsignedInt(address) >= 8192 && Short.toUnsignedInt(address) <= 16383){
			EaterEmulator.gfx.setCursor(Math.floorMod(Short.toUnsignedInt(address) - 8192, 256), Math.floorDiv(Short.toUnsignedInt(address) - 8192, 256));
			EaterEmulator.ram.write(address, data);
		} else {
			EaterEmulator.ram.write(address, data);
		}
	}
}
