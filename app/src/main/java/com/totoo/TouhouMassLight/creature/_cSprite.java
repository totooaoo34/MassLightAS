package com.totoo.TouhouMassLight.creature;
///////////////////////////////////////////////////////////////////////////////////////////////////
//
// cSprite.java
//
// This class is the main part of AuroraGT engine.
// All animations are processed here.
//
///////////////////////////////////////////////////////////////////////////////////////////////////

import android.media.Image;

//_DEBUG

//_DEBUG

//__DEFINE_H__

// ASprite.java - Aurora Sprite - MIDP 2.0 version
////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  Author(s): Ionut Matasaru (ionut.matasaru@gameloft.com)
//
////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  Implementation for sprites exported by AuroraGT editor.
//  Contains methods to handle displaying of strings.
//
//  History:
//		28.09.2003, created
//		03.10.2003, I16, I127
//		12.11.2003, I2, I4, I256
//		24.11.2003, Draw String System
//		22.12.2003, MIDP 2.0 version
//		23.04.2004, I256 bug fixed (&0xFF)
//		12.05.2004, modular version, 8888 pixel format
//		15.06.2004, improved (flip X, flip Y, flip XY)
//		24.06.2004, 4 bits extension for FModules -> module index => 4+8=12 bits => max. 4096 modules
//					4 bits extension for AFrames  -> frame index  => 4+8=12 bits => max. 4096 frames
//		06.07.2004, Nokia API version
//		23.08.2004, BSprite v3
//		10.09.2004, HyperFrames/HyperFModules, module mappings
//		15.09.2005, updated
//		19.09.2005, new data format: I256RLE; new pixel formats: _1555, _0565 (AuroraGT v053)
//		20.09.2005, _nModules, _modules_w[], _modules_h[] instead of _modules[]
//		14.12.2005, new data format: I64RLE (AuroraGT v056)
//
//  Features:
//		* use ".bsprite" files (BSPRITE_v003, exported by AuroraGT v0.5.4 - SpriteEditor v0.6.4 or later)
//		* BSprite flags: BS_DEFAULT_MIDP2
//		* pixel formats supported:
//			8888	- A8 R8 G8 B8
//			4444	- A4 R4 G4 B4
//			1555	- A1 R5 G5 B5
//			0565	- R5 G6 B5
//		* data formats supported:
//			I2		- 8 pixels/byte encoding (max 2 colors)
//			I4		- 4 pixels/byte encoding (max 4 colors)
//			I16		- 2 pixels/byte encoding (max 16 colors)
//			I64RLE	- variable RLE compression (max 64 colors)
//			I127RLE	- fixed RLE compression (max 127 colors)
//			I256	- 1 pixel/byte encoding (max 256 colors)
//			I256RLE	- fixed RLE compression (max 256 colors)
//
//  Note:
//		If you want to use the Draw String System, first init the "_map_char" for each font sprite.
//		It should contain a 256 byte array with the mapping between ASCII codes and FModules.
//		You could use FontTable.exe to generate it.
//
////////////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////////////////////////

public class _cSprite {
	// temporar buffer...
	// Redimension it depending on the max size of the modules!
	// static int temp[] = new int[8*1024];

	static int temp[];
	static short temp_short[];

	// ////////////////////////////////////////////////
	// Set these switches to best suite your game!
	// Unused code will be removed by the obfuscator!

	final static boolean USE_MODULE_MAPPINGS = false; // use Module Mappings ?

	final static boolean USE_HYPER_FM = true; // use Hyper Frame Modules ?

	final static boolean USE_INDEX_EX_FMODULES = true; // true|false -> max.
	// 1024|256 modules
	// refs. from a FModule
	public final static boolean USE_INDEX_EX_AFRAMES = true; // true|false -> max.
	// 1024|256 frames refs.
	// from an Anim

	final static boolean USE_PRECOMPUTED_FRAME_RECT = false;
	final static boolean ALWAYS_BS_FRAME_RECTS = true; // all sprites are
	// exported with
	// BS_FRAME_RECTS
	final static boolean ALWAYS_BS_NFM_1_BYTE = false; // all sprites are
	// exported with
	// BS_NFM_1_BYTE

	final static boolean ALWAYS_BS_SKIP_FRAME_RC = false; // all sprites are
	// exported with
	// BS_SKIP_FRAME_RC

	final static boolean ALWAYS_BS_NAF_1_BYTE = false; // all sprites are
	// exported with
	// BS_NAF_1_BYTE

	final static boolean USE_PIXEL_FORMAT_8888 = false;
	final static boolean USE_PIXEL_FORMAT_4444 = true;
	final static boolean USE_PIXEL_FORMAT_1555 = true;
	final static boolean USE_PIXEL_FORMAT_0565 = false;

	final static boolean USE_ENCODE_FORMAT_I2 = true;
	final static boolean USE_ENCODE_FORMAT_I4 = true;
	final static boolean USE_ENCODE_FORMAT_I16 = true;
	final static boolean USE_ENCODE_FORMAT_I256 = true;
	final static boolean USE_ENCODE_FORMAT_I64RLE = true;
	final static boolean USE_ENCODE_FORMAT_I127RLE = true;
	final static boolean USE_ENCODE_FORMAT_I256RLE = true;

	final static int MAX_SPRITE_PALETTES = 8;
	final static int MAX_MODULE_MAPPINGS = 16;

	// ////////////////////////////////////////////////

	final static short BSPRITE_v003 = (short) 0x03DF; // supported version

	// ////////////////////////////////////////////////
	// BSprite flags

	final static int BS_MODULES = (1 << 0);
	final static int BS_MODULES_XY = (1 << 1);
	final static int BS_MODULES_IMG = (1 << 2);
	final static int BS_FRAMES = (1 << 8);
	final static int BS_FM_OFF_SHORT = (1 << 10); // export fm offsets as
	// shorts
	final static int BS_NFM_1_BYTE = (1 << 11); // export nfm as byte
	final static int BS_SKIP_FRAME_RC = (1 << 12); // do not export frame rect
	final static int BS_ANIMS = (1 << 16);
	public final static int BS_AF_OFF_SHORT = (1 << 18); // export af offsets as
	// shorts
	final static int BS_NAF_1_BYTE = (1 << 19); // export naf as byte
	final static int BS_MODULE_IMAGES = (1 << 24);
	final static int BS_PNG_CRC = (1 << 25);
	final static int BS_KEEP_PAL = (1 << 26);
	final static int BS_TRANSP_FIRST = (1 << 27);
	final static int BS_TRANSP_LAST = (1 << 28);
	final static int BS_SINGLE_IMAGE = (1 << 29);

	final static int BS_DEFAULT_DOJA = (BS_MODULES | BS_FRAMES | BS_ANIMS);
	final static int BS_DEFAULT_MIDP2 = (BS_MODULES | BS_FRAMES | BS_ANIMS | BS_MODULE_IMAGES);
	final static int BS_DEFAULT_NOKIA = BS_DEFAULT_MIDP2;
	final static int BS_DEFAULT_MIDP1 = (BS_MODULES | BS_MODULES_XY | BS_FRAMES | BS_ANIMS);
	final static int BS_DEFAULT_MIDP1b = (BS_MODULES | BS_FRAMES | BS_ANIMS
			| BS_MODULE_IMAGES | BS_PNG_CRC);

	// ////////////////////////////////////////////////

	final static short PIXEL_FORMAT_8888 = (short) 0x8888;
	final static short PIXEL_FORMAT_4444 = (short) 0x4444;
	final static short PIXEL_FORMAT_1555 = (short) 0x5515;
	final static short PIXEL_FORMAT_0565 = (short) 0x6505;

	// ////////////////////////////////////////////////

	final static short ENCODE_FORMAT_I2 = 0x0200;
	final static short ENCODE_FORMAT_I4 = 0x0400;
	// final static short ENCODE_FORMAT_I8 = 0x0800;
	final static short ENCODE_FORMAT_I16 = 0x1600;
	// final static short ENCODE_FORMAT_I16MP = 0x16??;
	// final static short ENCODE_FORMAT_I32 = 0x3200;
	// final static short ENCODE_FORMAT_I64 = 0x6400;
	// final static short ENCODE_FORMAT_I128 = 0x2801;
	final static short ENCODE_FORMAT_I256 = 0x5602;
	// final static short ENCODE_FORMAT_I127_ = 0x2701;
	final static short ENCODE_FORMAT_I64RLE = 0x64F0;
	final static short ENCODE_FORMAT_I127RLE = 0x27F1;
	final static short ENCODE_FORMAT_I256RLE = 0x56F2;

	// ////////////////////////////////////////////////
	// Frames/Anims flags...

	public final static byte FLAG_FLIP_X = 0x01;
	final static byte FLAG_FLIP_Y = 0x02;
	final static byte FLAG_ROT_90 = 0x04;

	final static byte FLAG_USER0 = 0x10; // user flag 0
	final static byte FLAG_USER1 = 0x20; // user flag 1

	final static byte FLAG_HYPER_FM = 0x10; // Hyper FModule, used by FModules
	final static byte FLAG_ATTACK_BOX = 0x40; // attack box , used by FModules

	// Index extension...
	public final static int FLAG_INDEX_EX_MASK = 0xC0; // 11000000, bits 6, 7
	final static int INDEX_MASK = 0x03FF; // max 1024 values
	final static int INDEX_EX_MASK = 0x0300;
	public final static int INDEX_EX_SHIFT = 2;

	// ////////////////////////////////////////////////
	// flags passed as params...

	final static byte FLAG_OFFSET_FM = 0x10;
	final static byte FLAG_OFFSET_AF = 0x20;

	// ////////////////////////////////////////////////

	// Modules...
	int _nModules; // number of modules
	byte[] _modules_x; // x for each module [BS_MODULES_XY]
	byte[] _modules_y; // y for each module [BS_MODULES_XY]
	byte[] _modules_img_index;
	byte[] _modules_w; // width for each module
	byte[] _modules_h; // height for each module

	// Frames...
	byte[] _frames_nfm; // number of FModules (max 256 FModules/Frame)
	// short[] _frames_nfm; // number of FModules (max 65536 FModules/Frame)
	short[] _frames_fm_start; // index of the first FModule
	short[] _frames_rc_start; // index of frame rects
	byte[] _frames_rc; // frame bound rect (x y width height)
	byte[] _frames_view_rc; // frame view rect

	// FModules...

	short[] _fmodules; // 4 bytes for each FModule
	byte[] _fmodules_byte;

	// byte[] _fmodules_module; // 0 : module index
	// byte[] _fmodules_ox; // 1 : ox
	// byte[] _fmodules_oy; // 2 : oy
	// byte[] _fmodules_flags; // 3 : flags

	// Anims...
	byte[] _anims_naf; // number of AFrames (max 256 AFrames/Anim)
	// short[] _anims_naf; // number of AFrames (max 65536 AFrames/Anim)
	public short[] _anims_af_start; // index of the first AFrame

	// AFrames...
	public byte[] _aframes; // 5 bytes for each AFrame
	// byte[] _aframes_frame; // 0 : frame index
	// byte[] _aframes_time; // 1 : time
	// byte[] _aframes_ox; // 2 : ox
	// byte[] _aframes_oy; // 3 : oy
	// byte[] _aframes_flags; // 4 : flags

	// Module mappings...
	int[][] _map; // all mappings
	// int _mappings; // number of mapings
	private int _cur_map; // current mapping

	// Palettes...
	// short _pixel_format; // always converted to 8888

	int[][] _pal; // all palettes

	short[][] _pal_short; // for nokia ui

	byte[] _pal_data; // palettes data for BS_DEFAULT_MIDP1c
	int _palettes; // number of palettes
	int _colors; // number of colors
	public int _crt_pal; // current palette
	boolean _alpha; // has transparency ?
	public int _bs_flags; // sprite format
	// int _flags; // transparency, etc.

	// Graphics data (for each module)...
	// Image[] _main_image; // an image with all modules, for each palette
	short _data_format;
	int _i64rle_color_mask; // used by ENCODE_FORMAT_I64RLE
	int _i64rle_color_bits; // used by ENCODE_FORMAT_I64RLE
	byte[] _modules_data; // encoded image data for all modules
	int[] _modules_data_off; // offset for the image data of each module
	// int[][] _modules_data2; // cache image data (decoded)

	Image[][] _modules_image; // cache image for each module / with each
	// palette
	Image[][] _modules_trans_image;

	short[][][] _modules_image_short;
	// Image[] _modules_image_fx; // cache image for each module (flipped
	// horizontally)

	// gaofan add draw Transparency 2007.03.17
	public int _alphaStep;
	public boolean _drawTransparency;
	int _transparenceColor;

	public boolean _drawAlpDep;
	public boolean _drawRedAlp;
	public static final int TOTAL_EFFECT_PAL = 2;
	public static final int EFFECT_PAL_TRAN = 0;
	public static final int EFFECT_PAL_RED = 1;

	boolean _drawAlp = false;

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public _cSprite() {
		// _map = null;
		// _mappings = 0;
		// _cur_map = -1;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// Free all loaded resource, it's to be done.
	void Free() {
	}

//	public void Load(byte[] file, int offset) {
//
//		try {
//			// if (DEF.bDbgS)
//			// System.out.println("ASprite.Load(" + file.length + " bytes, "
//			// + offset + ")...");
//
//			System.gc();
//
//			short bs_version = (short) ((file[offset++] & 0xFF) + ((file[offset++] & 0xFF) << 8));
//			// if (DEF.bDbgS)
//			// System.out.println("bs_version = 0x"
//			// + Integer.toHexString(bs_version));
//			// if (DEF.bErr) {
//			// if (bs_version != BSPRITE_v003)
//			// System.out.println("ERROR: Invalid BSprite version !");
//			// }
//
//			_bs_flags = ((file[offset++] & 0xFF))
//					+ ((file[offset++] & 0xFF) << 8)
//					+ ((file[offset++] & 0xFF) << 16)
//					+ ((file[offset++] & 0xFF) << 24);
//			// if (DEF.bDbgS)
//			// System.out.println("_bs_flags = 0x"
//			// + Integer.toHexString(_bs_flags));
//			// ////////////////////////////
//
//			if ((_bs_flags & BS_MODULES) != 0) {
//				// Modules...
//				_nModules = (file[offset++] & 0xFF)
//						+ ((file[offset++] & 0xFF) << 8);
//				// if (DEF.bDbgS)
//				// System.out.println("nModules = " + _nModules);
//				if (_nModules > 0) {
//					if ((_bs_flags & BS_MODULES_XY) != 0) {
//						_modules_x = new byte[_nModules];
//						_modules_y = new byte[_nModules];
//					}
//					_modules_w = new byte[_nModules];
//					_modules_h = new byte[_nModules];
//					for (int i = 0; i < _nModules; i++) {
//						if ((_bs_flags & BS_MODULES_XY) != 0) {
//							_modules_x[i] = file[offset++];
//							_modules_y[i] = file[offset++];
//						}
//						_modules_w[i] = file[offset++];
//						_modules_h[i] = file[offset++];
//					}
//				}
//			}
//
//			// ////////////////////////////
//			if ((_bs_flags & BS_FRAMES) != 0) {
//				// FModules...
//				int nFModules = (file[offset++] & 0xFF)
//						+ ((file[offset++] & 0xFF) << 8);
//				// if (DEF.bDbgS)
//				// System.out.println("nFModules = " + nFModules);
//				if (nFModules > 0) {
//
//					if (config.k_sprite_use_fmodules_short) {
//
//						_fmodules = new short[nFModules << 2];
//
//						for (int i = 0; i < nFModules; i++) {
//							_fmodules[i * 4] = (short) file[offset++];
//							if ((_bs_flags & BS_FM_OFF_SHORT) != 0) {
//								_fmodules[i * 4 + 1] = (short) ((file[offset++] & 0xFF) + ((file[offset++] & 0xFF) << 8));
//								_fmodules[i * 4 + 2] = (short) ((file[offset++] & 0xFF) + ((file[offset++] & 0xFF) << 8));
//							} else {
//								_fmodules[i * 4 + 1] = (short) file[offset++];
//								_fmodules[i * 4 + 2] = (short) file[offset++];
//							}
//							_fmodules[i * 4 + 3] = (short) file[offset++];
//						}
//					} else {
//
//						_fmodules_byte = new byte[nFModules << 2];
//
//						for (int i = 0; i < nFModules; i++) {
//							_fmodules_byte[i * 4] = (byte) file[offset++];
//							// if ( (_bs_flags & BS_FM_OFF_SHORT) != 0)
//							// {
//							// _fmodules_byte[i * 4 + 1] = (short) (
//							// (file[offset++] & 0xFF) + ( (file[offset++] &
//							// 0xFF) << 8));
//							// _fmodules_byte[i * 4 + 2] = (short) (
//							// (file[offset++] & 0xFF) + ( (file[offset++] &
//							// 0xFF) << 8));
//							// }
//							// else
//							{
//								_fmodules_byte[i * 4 + 1] = (byte) file[offset++];
//								_fmodules_byte[i * 4 + 2] = (byte) file[offset++];
//							}
//							_fmodules_byte[i * 4 + 3] = (byte) file[offset++];
//						}
//					}
//
//				}
//
//				if (ALWAYS_BS_FRAME_RECTS) {
//					int nFRects = (file[offset++] & 0xFF)
//							+ ((file[offset++] & 0xFF) << 8);
//					if (nFRects > 0) {
//						_frames_rc = new byte[nFRects << 2];
//						System.arraycopy(file, offset, _frames_rc, 0,
//								_frames_rc.length);
//						offset += _frames_rc.length;
//					}
//				}
//
//				// Frames...
//				int nFrames = (file[offset++] & 0xFF)
//						+ ((file[offset++] & 0xFF) << 8);
//				// if (DEF.bDbgS)
//				// System.out.println("nFrames = " + nFrames);
//				if (nFrames > 0) {
//					_frames_nfm = new byte[nFrames];
//					_frames_fm_start = new short[nFrames];
//					short indexFRect = 0;
//					if (ALWAYS_BS_FRAME_RECTS) {
//						_frames_rc_start = new short[nFrames + 1];
//						_frames_rc_start[0] = indexFRect;
//					}
//					for (int i = 0; i < nFrames; i++) {
//						_frames_nfm[i] = file[offset++];
//						if (!ALWAYS_BS_NFM_1_BYTE)
//							offset++;
//						_frames_fm_start[i] = (short) ((file[offset++] & 0xFF) + ((file[offset++] & 0xFF) << 8));
//						if (ALWAYS_BS_FRAME_RECTS) {
//							indexFRect += file[offset++];
//							_frames_rc_start[i + 1] = indexFRect;
//						}
//					}
//
//					if (!ALWAYS_BS_SKIP_FRAME_RC) {
//						_frames_view_rc = new byte[nFrames << 2];
//						System.arraycopy(file, offset, _frames_view_rc, 0,
//								_frames_view_rc.length);
//						offset += _frames_view_rc.length;
//						// remove 2005.5.21 gao fan
//						/*
//						 * if (USE_PRECOMPUTED_FRAME_RECT) { // Bound rect for
//						 * each frame... int nFrames4 = nFrames << 2; _frames_rc
//						 * = new byte[nFrames4]; for (int i = 0; i < nFrames4;
//						 * i++) _frames_rc[i] = file[offset++]; } else offset +=
//						 * (nFrames << 2); //
//						 */
//					} else {
//						if (USE_PRECOMPUTED_FRAME_RECT) {
//							// TODO: precompute frame rc
//						}
//					}
//				}
//			}
//
//			// ////////////////////////////
//			if ((_bs_flags & BS_ANIMS) != 0) {
//				// AFrames...
//				int nAFrames = (file[offset++] & 0xFF)
//						+ ((file[offset++] & 0xFF) << 8);
//				// if (DEF.bDbgS)
//				// System.out.println("nAFrames = " + nAFrames);
//				if (nAFrames > 0) {
//					if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//
//						_aframes = new byte[nAFrames * 7];
//					} else {
//						_aframes = new byte[nAFrames * 5];
//
//					}
//					// _aframes = new byte[nAFrames * 5];
//					System
//							.arraycopy(file, offset, _aframes, 0,
//									_aframes.length);
//					offset += _aframes.length;
//				}
//
//				// Anims...
//				int nAnims = (file[offset++] & 0xFF)
//						+ ((file[offset++] & 0xFF) << 8);
//				// if (DEF.bDbgS)
//				// System.out.println("nAnims = " + nAnims);
//				if (nAnims > 0) {
//					_anims_naf = new byte[nAnims];
//					_anims_af_start = new short[nAnims];
//					for (int i = 0; i < nAnims; i++) {
//						_anims_naf[i] = file[offset++];
//						if (!ALWAYS_BS_NAF_1_BYTE)
//							offset++;
//						_anims_af_start[i] = (short) ((file[offset++] & 0xFF) + ((file[offset++] & 0xFF) << 8));
//					}
//				}
//			}
//
//			// ////////////////////////////
//
//			// if (_nModules <= 0)
//			// {
//			// if (DEF.bErr) System.out.println(
//			// "WARNING: sprite with num modules = " + _nModules);
//			// System.gc();
//			// return;
//			// }
//
//			// ////////////////////////////
//
//			if ((_bs_flags & BS_MODULE_IMAGES) != 0) {
//				// Pixel format (must be one of supported SPRITE_FORMAT_xxxx)...
//				short _pixel_format = (short) ((file[offset++] & 0xFF) + ((file[offset++] & 0xFF) << 8));
//				// if (DEF.bDbgS)
//				// System.out.println("_pixel_format = 0x"
//				// + Integer.toHexString(_pixel_format));
//
//				// Number of palettes...
//				_palettes = file[offset++] & 0xFF;
//				// if (DEF.bDbgS)
//				// System.out.println("_palettes = " + _palettes);
//
//				// Number of colors...
//				int colors = file[offset++] & 0xFF;
//				if (USE_ENCODE_FORMAT_I256)
//					if (colors == 0)
//						colors = 256;
//				// if (DEF.bDbgS)
//				// System.out.println("colors = " + colors);
//
//				// Palettes...
//
//				_pal = new int[_palettes][];
//
//				for (int p = 0; p < _palettes; p++) {
//					{
//
//						_pal[p] = new int[colors];
//
//						// HINT: Sort these pixel formats regarding how often
//						// are used by your game!
//						if ((_pixel_format == PIXEL_FORMAT_8888)
//								&& USE_PIXEL_FORMAT_8888) {
//							for (int c = 0; c < colors; c++) {
//								int _8888 = ((file[offset++] & 0xFF)); // B
//								_8888 += ((file[offset++] & 0xFF) << 8); // G
//								_8888 += ((file[offset++] & 0xFF) << 16); // R
//								_8888 += ((file[offset++] & 0xFF) << 24); // A
//
//								if ((_8888 & 0xFF000000) != 0xFF000000)
//									_alpha = true;
//
//								_pal[p][c] = _8888;
//							}
//							_transparenceColor = 0xFF00FF;
//						} else if ((_pixel_format == PIXEL_FORMAT_4444)
//								&& USE_PIXEL_FORMAT_4444) {
//							for (int c = 0; c < colors; c++) {
//								int _4444 = ((file[offset++] & 0xFF));
//								_4444 += ((file[offset++] & 0xFF) << 8);
//
//								if ((_4444 & 0xF000) != 0xF000)
//									_alpha = true;
//
//								// 4444 -> 8888
//								_pal[p][c] = ((_4444 & 0xF000) << 16)
//										| ((_4444 & 0xF000) << 12)
//										| // A
//										((_4444 & 0x0F00) << 12)
//										| ((_4444 & 0x0F00) << 8)
//										| // R
//										((_4444 & 0x00F0) << 8)
//										| ((_4444 & 0x00F0) << 4)
//										| // G
//										((_4444 & 0x000F) << 4)
//										| ((_4444 & 0x000F)); // B
//							}
//							_transparenceColor = 0xF000F0;
//						} else if ((_pixel_format == PIXEL_FORMAT_1555)
//								&& USE_PIXEL_FORMAT_1555) {
//							for (int c = 0; c < colors; c++) {
//								int _1555 = ((file[offset++] & 0xFF));
//								_1555 += ((file[offset++] & 0xFF) << 8);
//
//								int a = 0xFF000000;
//								if ((_1555 & 0x8000) != 0x8000) {
//									a = 0;
//									_alpha = true;
//								}
//
//								// 1555 -> 8888
//								_pal[p][c] = a | // A
//										((_1555 & 0x7C00) << 9) | // R
//										((_1555 & 0x03E0) << 6) | // G
//										((_1555 & 0x001F) << 3); // B
//							}
//							_transparenceColor = 0xF800F8;
//						} else if ((_pixel_format == PIXEL_FORMAT_0565)
//								&& USE_PIXEL_FORMAT_0565) {
//							for (int c = 0; c < colors; c++) {
//								int _0565 = ((file[offset++] & 0xFF));
//								_0565 += ((file[offset++] & 0xFF) << 8);
//
//								int a = 0xFF000000;
//								if (_0565 == 0xF81F) {
//									a = 0;
//									_alpha = true;
//								}
//
//								// 0565 -> 8888
//								_pal[p][c] = a | // A
//										((_0565 & 0xF800) << 8) | // R
//										((_0565 & 0x07E0) << 5) | // G
//										((_0565 & 0x001F) << 3); // B
//							}
//						}
//					}
//
//				}
//
//				// ////////////////////////////
//
//				// Data format (must be one of supported ENCODE_FORMAT_xxxx)...
//				_data_format = (short) ((file[offset++] & 0xFF) + ((file[offset++] & 0xFF) << 8));
//				// if (DEF.bDbgS)
//				// System.out.println("_data_format = 0x"
//				// + Integer.toHexString(_data_format));
//
//				if ((_data_format == ENCODE_FORMAT_I64RLE)
//						&& USE_ENCODE_FORMAT_I64RLE) {
//					// int clrs = colors - 1;
//					// _i64rle_color_mask = 1;
//					// _i64rle_color_bits = 1;
//					// while (clrs != 0)
//					// {
//					// clrs >>= 1;
//					// _i64rle_color_mask <<= 1;
//					// _i64rle_color_bits++;
//					// }
//					// _i64rle_color_mask--;
//
//					int clrs = colors - 1;
//					_i64rle_color_mask = 1;
//					_i64rle_color_bits = 0;
//					while (clrs != 0) {
//						clrs >>= 1;
//						_i64rle_color_mask <<= 1;
//						_i64rle_color_bits++;
//					}
//					_i64rle_color_mask--;
//				}
//
//				// Graphics data...
//				if (_nModules > 0) {
//					_modules_data_off = new int[_nModules];
//					int len = 0;
//					int off = offset;
//
//					for (int m = 0; m < _nModules; m++) {
//						// Image data for the module...
//						int size = (file[off++] & 0xFF)
//								+ ((file[off++] & 0xFF) << 8);
//
//						_modules_data_off[m] = len;
//						off += size;
//						len += size;
//					}
//
//					_modules_data = new byte[len];
//					for (int m = 0; m < _nModules; m++) {
//						// Image data for the module...
//						int size = (file[offset++] & 0xFF)
//								+ ((file[offset++] & 0xFF) << 8);
//						// if (DEF.bDbgS)
//						// System.out.println("frame[" + m + "] size = "
//						// + size);
//
//						System.arraycopy(file, offset, _modules_data,
//								_modules_data_off[m], size);
//						offset += size;
//					}
//				}
//			}
//
//			if ((_bs_flags & BS_SINGLE_IMAGE) != 0) {
//				// read png size
//				int size = (file[offset++] & 0xFF)
//						+ ((file[offset++] & 0xFF) << 8);
//				// if (DEF.bDbgS)
//				// System.out.println("png size = " + size);
//
//				// read the png data
//				_modules_data = new byte[size];
//				System.arraycopy(file, offset, _modules_data, 0, size);
//				offset += size;
//
//				// Number of palettes...
//				_palettes = file[offset++] & 0xFF;
//				// if (DEF.bDbgS)
//				// System.out.println("_palettes = " + _palettes);
//
//				// Number of colors...
//				_colors = file[offset++] & 0xFF;
//				if (_colors == 0)
//					_colors = 256;
//				// if (DEF.bDbgS)
//				// System.out.println("colors = " + _colors);
//
//				// Palettes...
//				int pal_size = _colors * 3 + 4 + _colors + 4;
//				// if (DEF.bDbgS)
//				// System.out.println("pal_size = " + pal_size);
//				_pal_data = new byte[_palettes * pal_size];
//				System.arraycopy(file, offset, _pal_data, 0, _palettes
//						* pal_size);
//				// if (DEF.bDbgS)
//				// System.out.println("...read pal data");
//				offset += _palettes * pal_size;
//			}
//
//			// ////////////////////////////
//			// module mappings
//
//			if (USE_MODULE_MAPPINGS) {
//				_map = new int[MAX_MODULE_MAPPINGS][];
//				// _mappings = 0;
//				_cur_map = -1;
//			}
//
//			// ////////////////////////////
//
//			// if (DEF.bDbgS)
//			// System.out.println("--- ok");
//			System.gc();
//
//			/*
//			 * // Used to adjust the size of temp[]... int max = 0, max_w = 0,
//			 * max_h = 0, max_m = 0; for (int m = 0; m < _nModules; m++) { int w
//			 * = (_modules_w[m]&0xFF); int h = (_modules_h[m]&0xFF); if (w <= 0
//			 * || h <= 0) continue; if (w * h > max) { max_w = w; max_h = h;
//			 * max_m = m; max = w * h; } } if (max > temp_max_size) {
//			 * temp_max_size = max; System.out.println("temp_max_size = " +
//			 * temp_max_size + " (" + max_w + "x" + max_h + ")" + " module = " +
//			 * (max_m+1) + "/" + nModules); }
//			 */
//		} catch (Exception e) {
//			e.printStackTrace();
//			// if (DEF.bErr) DBG.CatchException(e, "ASprite.Load()");
//		}
//	}

	// static int temp_max_size = 0;
	public void ClearCompressedImageData() {
		_modules_data = null;
		_modules_data_off = null;

		if ((_bs_flags & BS_SINGLE_IMAGE) != 0) {
			_pal_data = null;
		}

		System.gc();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// pal = palette to be initailized
	// m1 = first module
	// m2 = last module (-1 -> to end)
	// pal_copy = mapping to another palette (-1 -> build)

//	public void BuildCacheImages(int pal, int m1, int m2, int pal_copy) {
//		// if (_nModules == 0) return;
//
//		if (m2 == -1)
//			m2 = _nModules - 1;
//
//		{
//			if (_modules_image == null)
//				_modules_image = new Image[_palettes][];
//
//			if (_drawTransparency && _modules_trans_image == null) {
//				_modules_trans_image = new Image[TOTAL_EFFECT_PAL][];
//			}
//		}
//
//		if ((_bs_flags & BS_MODULE_IMAGES) != 0) {
//
//			{
//				if (_modules_image[pal] == null)
//					_modules_image[pal] = new Image[_nModules];
//
//				if (_drawTransparency && _modules_trans_image[pal] == null) {
//					_modules_trans_image[pal] = new Image[_nModules];
//				}
//
//			}
//
//			if (pal_copy >= 0) {
//				for (int i = m1; i <= m2; i++)
//					_modules_image[pal][i] = _modules_image[pal_copy][i];
//			} else {
//				int old_pal = _crt_pal;
//				_crt_pal = pal;
//				int total_area, total_size;
//				long mem;
//				// if (DEF.bDbgO)
//				// total_area = 0;
//				// if (DEF.bDbgO)
//				// total_size = 0;
//				System.gc();
//				// if (DEF.bDbgO)
//				// mem = Runtime.getRuntime().freeMemory();
//				for (int i = m1; i <= m2; i++) {
//					int sizeX = _modules_w[i] & 0xFF;
//					int sizeY = _modules_h[i] & 0xFF;
//					if (sizeX <= 0 || sizeY <= 0)
//						continue;
//
//					short[] image_data_short = null;
//					int[] image_data = null;
//
//					{
//						image_data = DecodeImage(i, 0);
//					}
//
//					if (image_data == null && image_data_short == null)
//						continue;
//
//					boolean bAlpha = false;
//					int size = sizeX * sizeY;
//					// if (DEF.bDbgO)/+= size;
//
//					{
//
//						for (int ii = 0; ii < size; ii++) {
//							if ((image_data[ii] & 0xFF000000) != 0xFF000000) {
//								bAlpha = true;
//								break;
//							}
//						}
//						// if (DEF.bDbgO)
//						// total_size += ((bAlpha & DEF.bEmu) ? (size * 3)
//						// : (size * 2));
//
//						if (_drawTransparency || _drawRedAlp) {
//							_modules_trans_image[pal][i] = Image
//									.createRGBImage(image_data, sizeX, sizeY,
//											bAlpha);
//						} else {
//							_modules_image[pal][i] = Image.createRGBImage(
//									image_data, sizeX, sizeY, bAlpha);
//						}
//
//						image_data = null;
//					}
//
//				}
//				System.gc();
//				// if (DEF.bDbgI)
//				// mem -= Runtime.getRuntime().freeMemory();
//				// if (DEF.bDbgI)
//				// System.out.println(" area = " + total_area + " pixels");
//				// if (DEF.bDbgI)
//				// System.out.println(" size = " + total_size + " bytes");
//				// if (DEF.bDbgI)
//				// System.out.println(" mem used = " + mem + " bytes");
//				// if (DEF.bDbgI)
//				// System.out.println(" images = " + (m2 - m1 + 1));
//				// if (DEF.bDbgI)
//				// System.out.println(" total overhead = "
//				// + (mem - total_size) + " bytes");
//				// if (DEF.bDbgI)
//				// System.out.println(" image overhead = "
//				// + ((mem - total_size) / (m2 - m1 + 1)) + " bytes");
//				_crt_pal = old_pal;
//			}
//		}
//
//		if ((_bs_flags & BS_SINGLE_IMAGE) != 0) {
//
//			{
//				if (_modules_image[pal] == null)
//					_modules_image[pal] = new Image[1];
//			}
//
//			int _PLTE_size = _colors * 3 + 4;
//			int _TRNS_size = _colors + 4;
//
//			// copy palette
//			System.arraycopy(_pal_data, pal * (_PLTE_size + _TRNS_size),
//					_modules_data, 41, _PLTE_size);
//			// copy transparency
//			System.arraycopy(_pal_data, pal * (_PLTE_size + _TRNS_size)
//					+ _PLTE_size, _modules_data, 41 + _PLTE_size + 8,
//					_TRNS_size);
//
//			{
//				_modules_image[pal][0] = Image.createImage(_modules_data, 0,
//						_modules_data.length);
//			}
//
//			// if (DEF.bDbgI)
//			// {
//			// _images_count++;
//			// _images_size += _modules_image[pal][0].getWidth() *
//			// _modules_image[pal][0].getHeight();
//			// }
//		}
//
//		System.gc();
//	}

//	void BuildTransImages(int pal, int m1, int m2, int pal_copy) {
//
//		if (pal == EFFECT_PAL_TRAN) {
//			_drawTransparency = true;
//			_alphaStep = cActor.FRAMES_ALP[0];
//			BuildCacheImages(pal, m1, m2, -1);
//			_drawTransparency = false;
//
//		} else if (pal == EFFECT_PAL_TRAN) {
//			_drawRedAlp = true;
//			_alphaStep = cActor.FRAMES_ALP[0];
//			BuildCacheImages(pal, m1, m2, -1);
//			_drawRedAlp = false;
//		}
//
//	}

//	void BuildCacheAnim(int anim) {
//		for (int i = 0; i < GetAFrames(anim); i++) {
//			int off = (_anims_af_start[anim] + i) * 5;
//			int frame = (_aframes[off] & 0xFF);
//			int nFModules = (_frames_nfm[frame] & 0xFF);
//
//			for (int fmodule = 0; fmodule < nFModules; fmodule++) {
//				int offset = (_frames_fm_start[frame] + fmodule) << 2;
//				int index = (config.k_sprite_use_fmodules_short) ? (_fmodules[offset] & 0xFF)
//						: (_fmodules_byte[offset] & 0xFF);
//
//				BuildCacheImages(_crt_pal, index, index, -1);
//			}
//		}
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * public String toString() { String str = new String();
	 * 
	 * if (DEF.bDbgM) { // Memory usage... int nModulesMem = 0; for (int i = 0;
	 * i < _nModules; i++) nModulesMem += (_modules_w[i]&0xFF) *
	 * (_modules_h[i]&0xFF); str = "encoded/decoded: " + _modules_data.length +
	 * "/" + ((DEF.bEmu ? 3 : 2) * nModulesMem); }
	 * 
	 * return str; }
	 */
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// Module Mapping...
	private void MODULE_MAPPING___() {
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	void SetModuleMapping(int map, byte[] mmp) {
		// if (DEF.bASSERT) DBG.ASSERT(map >= 0 && map < _mappings, "map >= 0 &&
		// map < _mappings");
		if (_map[map] == null) {
			_map[map] = new int[_nModules];
			for (int i = 0; i < _nModules; i++)
				_map[map][i] = i;
		}
		if (mmp == null)
			return;
		int off = 0;
		while (off < mmp.length) {
			int i1 = ((mmp[off++] & 0xFF) + ((mmp[off++] & 0xFF) << 8));
			int i2 = ((mmp[off++] & 0xFF) + ((mmp[off++] & 0xFF) << 8));
			_map[map][i1] = i2;
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	void ApplyModuleMapping(int dst_pal, int src_pal, byte[] mmp) {
		int off = 0;
		while (off < mmp.length) {
			int i1 = ((mmp[off++] & 0xFF) + ((mmp[off++] & 0xFF) << 8));
			int i2 = ((mmp[off++] & 0xFF) + ((mmp[off++] & 0xFF) << 8));
			_modules_image[dst_pal][i1] = _modules_image[src_pal][i2];
		}
		System.gc();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	void SetCurrentMMapping(int map) {
		_cur_map = map;
	}

	int GetCurrentMMapping() {
		return _cur_map;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	private void ___MODULE_MAPPING() {
	}

	// ... Module Mapping
	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public int GetAFrameTime(int anim, int aframe) {
		// int af = (_anims_af_start[anim] + aframe);
		// return _aframes[((af<<2) + af + 1)] & 0xFF;
		// return _aframes[(_anims_af_start[anim] + aframe) * 5 + 1] & 0xFF;

		if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {

			return _aframes[(_anims_af_start[anim] + aframe) * 7 + 1] & 0xFF;
		} else {

			return _aframes[(_anims_af_start[anim] + aframe) * 5 + 1] & 0xFF;
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public int GetAFrames(int anim) {
		return _anims_naf[anim] & 0xFF;
		// return _anims_naf[anim]&0xFFFF;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	int GetFModules(int frame) {
		return _frames_nfm[frame] & 0xFF;
		// return _frames_nfm[frame]&0xFFFF;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	int GetModuleWidth(int module) {
		return _modules_w[module] & 0xFF;
	}

	int GetModuleHeight(int module) {
		return _modules_h[module] & 0xFF;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	int GetFrameWidth(int frame) {
		return _frames_rc[frame * 4 + 2] & 0xFF;
	}

	int GetFrameHeight(int frame) {
		return _frames_rc[frame * 4 + 3] & 0xFF;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	short GetFrameModuleX(int frame, int fmodule) {
//		int off = (_frames_fm_start[frame] + fmodule) << 2;
//		return (config.k_sprite_use_fmodules_short) ? _fmodules[off + 1]
//				: _fmodules_byte[off + 1];
//	}
//
//	short GetFrameModuleY(int frame, int fmodule) {
//		int off = (_frames_fm_start[frame] + fmodule) << 2;
//		return (config.k_sprite_use_fmodules_short) ? _fmodules[off + 2]
//				: _fmodules_byte[off + 2];
//	}

//	int GetFrameModuleWidth(int frame, int fmodule) {
//		int off = (_frames_fm_start[frame] + fmodule) << 2;
//		int index = (config.k_sprite_use_fmodules_short) ? _fmodules[off] & 0xFF
//				: _fmodules_byte[off] & 0xFF;
//		if (USE_INDEX_EX_FMODULES)
//			index |= ((((config.k_sprite_use_fmodules_short) ? _fmodules[off + 3] & 0xFF
//					: _fmodules_byte[off + 3] & 0xFF) & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//		return _modules_w[index] & 0xFF;
//	}

//	int GetFrameModuleHeight(int frame, int fmodule) {
//		int off = (_frames_fm_start[frame] + fmodule) << 2;
//		int index = (config.k_sprite_use_fmodules_short) ? _fmodules[off] & 0xFF
//				: _fmodules_byte[off] & 0xFF;
//		if (USE_INDEX_EX_FMODULES)
//			index |= ((((config.k_sprite_use_fmodules_short) ? _fmodules[off + 3] & 0xFF
//					: _fmodules_byte[off + 3] & 0xFF) & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//		return _modules_h[index] & 0xFF;
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	// int GetAnimFrame(int anim, int aframe)
	// {
	// int off = (_anims_af_start[anim] + aframe) * 5;
	// int frame = _aframes[off] & 0xFF;
	// if (USE_INDEX_EX_AFRAMES)
	// frame |= ((_aframes[off + 4] & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
	// return frame;
	// }

	// //////////////////////////////////////////////////////////////////////////////////////////////////
//	public void GetAFrameRect(short[] rc, int anim, int aframe, int rectIndex,
//			int flags, int hx, int hy) {
//		// System.out.println("GetAFrameRect(rc, "+anim+", "+aframe+", "+posX+",
//		// "+posY+", 0x"+Integer.toHexString(flags)+", "+hx+", "+hy+")");
//		// int off = (_anims_af_start[anim] + aframe) * 5;
//		int off;
//		if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//			off = (_anims_af_start[anim] + aframe) * 7;
//		} else {
//			off = (_anims_af_start[anim] + aframe) * 5;
//		}
//		int frame = _aframes[off] & 0xFF;
//		if (USE_INDEX_EX_AFRAMES) {
//			if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//				frame |= ((_aframes[off + 6] & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//			} else {
//				frame |= ((_aframes[off + 4] & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//			}
//		}
//		// if ((flags & FLAG_OFFSET_AF) != 0)
//		// {
//		// if ((flags & FLAG_FLIP_X) != 0) hx += _aframes[off+2];
//		// else hx -= _aframes[off+2];
//		// if ((flags & FLAG_FLIP_Y) != 0) hy += _aframes[off+3];
//		// else hy -= _aframes[off+3];
//
//		if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//			if ((flags & FLAG_FLIP_X) != 0)
//				hx += ((_aframes[off + 2] & 0xFF) + (_aframes[off + 3] << 8));// m_fmodules[off+1];
//			else
//				hx -= (_aframes[off + 2] & 0xFF) + (_aframes[off + 3] << 8);// m_fmodules[off+1];
//			if ((flags & FLAG_FLIP_Y) != 0)
//				hy += ((_aframes[off + 4] & 0xFF) + (_aframes[off + 5] << 8));// m_fmodules[off+2];
//			else
//				hy -= (_aframes[off + 4] & 0xFF) + (_aframes[off + 5] << 8);// m_fmodules[off+2];
//
//		} else {
//			if ((flags & FLAG_FLIP_X) != 0)
//				hx += _aframes[off + 2];
//			else
//				hx -= _aframes[off + 2];
//			if ((flags & FLAG_FLIP_Y) != 0)
//				hy += _aframes[off + 3];
//			else
//				hy -= _aframes[off + 3];
//
//		}
//		// }
//		// if ((flags & FLAG_FLIP_X) != 0) hx += _frames_w[frame]&0xFF;
//		// if ((flags & FLAG_FLIP_Y) != 0) hy += _frames_h[frame]&0xFF;
//
//		if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//
//			GetFrameRect(rc, frame, rectIndex, flags
//					^ (_aframes[off + 6] & 0x0F), hx, hy);
//
//		} else {
//			GetFrameRect(rc, frame, rectIndex, flags
//					^ (_aframes[off + 4] & 0x0F), hx, hy);
//
//		}
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	public void GetFrameRect(short[] rc, int frame, int rectIndex, int flags, int hx,
//			int hy) {
//		// System.out.println("GetFrameRect(rc, "+frame+", "+posX+", "+posY+",
//		// 0x"+Integer.toHexString(flags)+", "+hx+", "+hy+")");
//
//		if (/* (USE_PRECOMPUTED_FRAME_RECT || ALWAYS_BS_FRAME_RECTS) && */rectIndex != -1)
//
//		{
//			if (ALWAYS_BS_FRAME_RECTS) {
//				rectIndex += _frames_rc_start[frame];
//				if (_frames_rc_start[frame + 1] <= rectIndex) {
//					rc[0] = rc[1] = rc[2] = rc[3] = 0;
//					return;
//				}
//				frame = rectIndex;
//			}
//			int frame4 = frame << 2;
//			int fx = _frames_rc[frame4++];
//			int fy = _frames_rc[frame4++];
//			int fw = _frames_rc[frame4++] & 0xFF;
//			int fh = _frames_rc[frame4++] & 0xFF;
//
//			if ((flags & FLAG_FLIP_X) != 0)
//				hx += fx + fw;
//			else
//				hx -= fx;
//			if ((flags & FLAG_FLIP_Y) != 0)
//				hy += fy + fh;
//			else
//				hy -= fy;
//
//			rc[0] = (short) -hx;
//			rc[1] = (short) -hy;
//			rc[2] = (short) (rc[0] + fw);
//			rc[3] = (short) (rc[1] + fh);
//		}
//
//		else {
//			// old
//			// int fx = (255 << DEF.FIXED_PRECISION);
//			// int fy = (255 << DEF.FIXED_PRECISION);
//			// new
//
//			if (ALWAYS_BS_SKIP_FRAME_RC || (_bs_flags & BS_FM_OFF_SHORT) != 0) {
//				int fx = 0;
//				int fy = 0;
//				int fw = 0;
//				int fh = 0;
//
//				int nFModules = _frames_nfm[frame] & 0xFF;
//				// int nFModules = _frames_nfm[frame]&0xFFFF;
//				for (int fmodule = 0; fmodule < nFModules; fmodule++) {
//					GetFModuleRect(rc, frame, fmodule);
//					// old
//					// if (rc[0] < fx) fx = rc[0];
//					// if (rc[1] < fy) fy = rc[1];
//					// if (rc[2] > fx + fw) fw = rc[2] - fx;
//					// if (rc[3] > fy + fh) fh = rc[3] - fy;
//					// new
//					if (rc[0] < fx) {
//						fw = (fx + fw) - rc[0];
//						fx = rc[0];
//					}
//					if (rc[1] < fy) {
//						fh = (fy + fh) - rc[1];
//						fy = rc[1];
//					}
//					if (rc[2] > fx + fw) {
//						fw = rc[2] - fx;
//					}
//					if (rc[3] > fy + fh) {
//						fh = rc[3] - fy;
//					}
//				}
//				if ((flags & FLAG_FLIP_X) != 0)
//					hx += fx + fw;
//				else
//					hx -= fx;
//				if ((flags & FLAG_FLIP_Y) != 0)
//					hy += fy + fh;
//				else
//					hy -= fy;
//
//				rc[0] = (short) -hx;
//				rc[1] = (short) -hy;
//				rc[2] = (short) (rc[0] + fw);
//				rc[3] = (short) (rc[1] + fh);
//			} else {
//				int frame4 = frame << 2;
//				rc[0] = (short) (_frames_view_rc[frame4++] - hx);
//				rc[1] = (short) (_frames_view_rc[frame4++] - hy);
//				rc[2] = (short) (rc[0] + _frames_view_rc[frame4++]);
//				rc[3] = (short) (rc[1] + _frames_view_rc[frame4++]);
//			}
//		}
//
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	void GetFModuleRect(short[] rc, int frame, int fmodule) {
//		int off = (_frames_fm_start[frame] + fmodule) << 2;
//
//		int fm_flags = (config.k_sprite_use_fmodules_short) ? _fmodules[off + 3] & 0xFF
//				: _fmodules_byte[off + 3] & 0xFF;
//		int index = (config.k_sprite_use_fmodules_short) ? _fmodules[off] & 0xFF
//				: _fmodules_byte[off] & 0xFF;
//
//		if (USE_INDEX_EX_FMODULES)
//			index |= ((fm_flags & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//
//		if (USE_HYPER_FM && ((fm_flags & FLAG_HYPER_FM) != 0)) {
//			GetFrameRect(rc, index, -1, 0, 0, 0);
//		} else {
//			rc[0] = GetFrameModuleX(frame, fmodule);
//			rc[1] = GetFrameModuleY(frame, fmodule);
//			rc[2] = (short) (rc[0] + GetFrameModuleWidth(frame, fmodule));
//			rc[3] = (short) (rc[1] + GetFrameModuleHeight(frame, fmodule));
//		}
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public void GetModuleRect(short[] rc, int module, int flags) {
		// System.out.println("GetModuleRect(rc, "+module+", "+posX+", "+posY+",
		// 0x"+Integer.toHexString(flags)+")");
		rc[0] = 0;
		rc[1] = 0;
		rc[2] = (short) (_modules_w[module] & 0xFF);
		rc[3] = (short) (_modules_h[module] & 0xFF);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	public void PaintAFrame(Graphics g, int anim, int aframe, int posX, int posY,
//			int flags, int hx, int hy) {
//		// System.out.println("PaintAFrame(g, "+anim+", "+aframe+", "+posX+",
//		// "+posY+", 0x"+Integer.toHexString(flags)+", "+hx+", "+hy+")");
//		int off;
//		if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//			off = (_anims_af_start[anim] + aframe) * 7;
//		} else {
//			off = (_anims_af_start[anim] + aframe) * 5;
//		}
//		int frame = _aframes[off] & 0xFF;
//		if (USE_INDEX_EX_AFRAMES) {
//			if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//				frame |= ((_aframes[off + 6] & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//			} else {
//				frame |= ((_aframes[off + 4] & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//			}
//		}
//
//		// if ((flags & FLAG_OFFSET_AF) != 0)
//		{
//			// if ((flags & FLAG_FLIP_X) != 0) hx += _aframes[off+2];
//			// else hx -= _aframes[off+2];
//			// if ((flags & FLAG_FLIP_Y) != 0) hy += _aframes[off+3];
//			// else hy -= _aframes[off+3];
//
//			if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//				if ((flags & FLAG_FLIP_X) != 0)
//					posX -= ((_aframes[off + 2] & 0xFF) + (_aframes[off + 3] << 8));// m_fmodules[off+1];
//				else
//					posX += (_aframes[off + 2] & 0xFF)
//							+ (_aframes[off + 3] << 8);// m_fmodules[off+1];
//				if ((flags & FLAG_FLIP_Y) != 0)
//					posY -= ((_aframes[off + 4] & 0xFF) + (_aframes[off + 5] << 8));// m_fmodules[off+2];
//				else
//					posY += (_aframes[off + 4] & 0xFF)
//							+ (_aframes[off + 5] << 8);// m_fmodules[off+2];
//
//			} else {
//				if ((flags & FLAG_FLIP_X) != 0)
//					hx += _aframes[off + 2];
//				else
//					hx -= _aframes[off + 2];
//				if ((flags & FLAG_FLIP_Y) != 0)
//					hy += _aframes[off + 3];
//				else
//					hy -= _aframes[off + 3];
//
//			}
//		}
//
//		// if ((flags & FLAG_FLIP_X) != 0) hx += _frames_w[frame]&0xFF;
//		// if ((flags & FLAG_FLIP_Y) != 0) hy += _frames_h[frame]&0xFF;
//		// PaintFrame(g, frame, posX-hx, posY-hy, flags ^ (_aframes[off+4] &
//		// 0x0F), hx, hy);
//
//		if ((_bs_flags & BS_AF_OFF_SHORT) != 0) {
//
//			PaintFrame(g, frame, posX - hx, posY - hy, flags
//					^ (_aframes[off + 6] & 0x0F), hx, hy);
//		} else {
//
//			PaintFrame(g, frame, posX - hx, posY - hy, flags
//					^ (_aframes[off + 4] & 0x0F), hx, hy);
//		}
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	public void PaintFrame(Graphics g, int frame, int posX, int posY, int flags,
//			int hx, int hy) {
//		// System.out.println("PaintFrame(g, "+frame+", "+posX+", "+posY+",
//		// 0x"+Integer.toHexString(flags)+", "+hx+", "+hy+")");
//		int nFModules = _frames_nfm[frame] & 0xFF;
//		// int nFModules = _frames_nfm[frame]&0xFFFF;
//		for (int fmodule = 0; fmodule < nFModules; fmodule++)
//			PaintFModule(g, frame, fmodule, posX, posY, flags, hx, hy);
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	void PaintFModule(Graphics g, int frame, int fmodule, int posX, int posY,
//			int flags, int hx, int hy) {
//		// System.out.println("PaintFModule(g, "+frame+", "+fmodule+", "+posX+",
//		// "+posY+", 0x"+Integer.toHexString(flags)+", "+hx+", "+hy+")");
//		int off = (_frames_fm_start[frame] + fmodule) << 2;
//		// int off = (_frames[(frame<<1)+1] + fmodule) << 2;
//
//		int fm_flags = (config.k_sprite_use_fmodules_short) ? _fmodules[off + 3] & 0xFF
//				: _fmodules_byte[off + 3] & 0xFF;
//		int index = (config.k_sprite_use_fmodules_short) ? _fmodules[off] & 0xFF
//				: _fmodules_byte[off] & 0xFF;
//		if (USE_INDEX_EX_FMODULES)
//			index |= ((fm_flags & FLAG_INDEX_EX_MASK) << INDEX_EX_SHIFT);
//
//		if (config.k_sprite_use_fmodules_short) {
//			if ((flags & FLAG_FLIP_X) != 0)
//				posX -= _fmodules[off + 1];
//			else
//				posX += _fmodules[off + 1];
//			if ((flags & FLAG_FLIP_Y) != 0)
//				posY -= _fmodules[off + 2];
//			else
//				posY += _fmodules[off + 2];
//		} else {
//			if ((flags & FLAG_FLIP_X) != 0)
//				posX -= _fmodules_byte[off + 1];
//			else
//				posX += _fmodules_byte[off + 1];
//			if ((flags & FLAG_FLIP_Y) != 0)
//				posY -= _fmodules_byte[off + 2];
//			else
//				posY += _fmodules_byte[off + 2];
//		}
//
//		if (USE_HYPER_FM && ((fm_flags & FLAG_HYPER_FM) != 0)) {
//			// if ((flags & FLAG_FLIP_X) != 0) posX -= _frames[(index<<?)
//			// ]&0xFF; // pF->w
//			// if ((flags & FLAG_FLIP_Y) != 0) posY -=
//			// _frames[(index<<?)+1]&0xFF; // pF->h
//
//			PaintFrame(g, index, posX, posY, flags ^ (fm_flags & 0x0F), hx, hy);
//		} else {
//			if ((flags & FLAG_FLIP_X) != 0)
//				posX -= _modules_w[index] & 0xFF;
//			if ((flags & FLAG_FLIP_Y) != 0)
//				posY -= _modules_h[index] & 0xFF;
//
//			PaintModule(g, index, posX, posY, flags ^ (fm_flags & 0x0F));
//		}
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	Image CreateModuleImage(int module, int posX, int posY, int flags)
//
//	{
//		if (USE_MODULE_MAPPINGS) {
//			// Apply current module mapping...
//			if (_cur_map >= 0) {
//				module = _map[_cur_map][module];
//			}
//		}
//
//		int sizeX = _modules_w[module] & 0xFF;
//		int sizeY = _modules_h[module] & 0xFF;
//		if (sizeX <= 0 || sizeY <= 0)
//			return null;
//
//		Image img = null;
//
//		// Try to use cached images...
//		if ((_modules_image != null) &&
//		// (_crt_pal < _modules_image.length) &&
//				(_modules_image[_crt_pal] != null))
//			img = _modules_image[_crt_pal][module];
//
//		// Build RGB image...
//		if (img == null) {
//
//			int[] image_data = DecodeImage(module, flags);
//
//			if (image_data == null) {
//				// if (DEF.bErr)
//				// System.out.println("DecodeImage() FAILED !");
//				return null;
//			}
//			// g.drawRGB(image_data, 0, sizeX, posX, posY, sizeX, sizeY,
//			// _alpha);
//
//			img = Image.createRGBImage(image_data, sizeX, sizeY, _alpha);
//
//		}
//		return img;
//	}

//	public void PaintModule(Graphics g, int module, int posX, int posY, int flags) {
//		// System.out.println("PaintModule(g, "+module+", "+posX+", "+posY+",
//		// 0x"+Integer.toHexString(flags)+")");
//		// System.out.println("PaintModule(module = "+module+", _crt_pal =
//		// "+_crt_pal+")...");
//
//		if (USE_MODULE_MAPPINGS) {
//			// Apply current module mapping...
//			if (_cur_map >= 0) {
//				// if (DEF.bASSERT) DBG.ASSERT(_cur_map < _mappings, "_cur_map <
//				// _mappings");
//				// if (DEF.bASSERT) DBG.ASSERT(_map[_cur_map] != null,
//				// "_map[_cur_map] != null");
//				module = _map[_cur_map][module];
//				// System.out.println("module -> "+module);
//			}
//		}
//
//		// if (DEF.bASSERT) DBG.ASSERT(module >= 0, "module >= 0");
//		// if (DEF.bASSERT) DBG.ASSERT(module < _nModules, "module <
//		// _nModules");
//		int sizeX = _modules_w[module] & 0xFF;
//		int sizeY = _modules_h[module] & 0xFF;
//		if (sizeX <= 0 || sizeY <= 0)
//			return;
//		/*
//		 * int cx = g.getClipX(); int cy = g.getClipY(); int cw =
//		 * g.getClipWidth(); int ch = g.getClipHeight(); // Fast visibility
//		 * test... if (posX + sizeX < cx || posY + sizeY < cy || posX >= cx + cw
//		 * || posY >= cy + ch) { // System.out.println("outside clip rect");
//		 * return; }
//		 */
//
//		Image img = null;
//		short[] img_short = null;
//
//		// Try to use cached images...
//		if (_drawTransparency) {
//			img = _modules_trans_image[EFFECT_PAL_TRAN][module];
//		} else if (_drawRedAlp) {
//			img = _modules_trans_image[EFFECT_PAL_RED][module];
//		} else {
//			if ((_modules_image != null) && (_modules_image[_crt_pal] != null)) {
//				if ((_bs_flags & BS_SINGLE_IMAGE) != 0) {
//					img = _modules_image[_crt_pal][0];
//				} else {
//
//					img = _modules_image[_crt_pal][module];
//				}
//			}
//		}
//		// USE_SPRITE_CACHE_POOL
//		// Build RGB image...
//
//		if (_drawTransparency && img == null)
//			return;
//		if ((img == null && img_short == null)) {
//
//			int[] image_data = null;
//			short[] image_data_short = null;
//
//			{
//				image_data = DecodeImage(module, flags);
//			}
//
//			if (image_data == null && image_data_short == null) {
//				// if (DEF.bErr)
//				// System.out.println("DecodeImage() FAILED !");
//				return;
//			}
//
//			if (sizeX + posX > 0 && sizeY + posY > 0) {
//				int off = 0, scanLen = sizeX;
//				if (posY < 0) {
//					off -= posY * sizeX;
//					sizeY += posY;
//					posY = 0;
//				}
//				if (posX < 0) {
//					off -= posX;
//					sizeX += posX;
//					posX = 0;
//				}
//
//				{
//
//					g.drawRGB(image_data, off, scanLen, posX, posY, sizeX,
//							sizeY, _alpha);
//
//				}
//			}
//
//			// img = Image.createRGBImage(image_data, sizeX, sizeY, _alpha);
//			image_data = null;
//			return;
//		}
//
//		{
//
//			sizeX = img.getWidth();
//			sizeY = img.getHeight();
//
//			int x = 0;
//			int y = 0;
//			if ((_bs_flags & BS_SINGLE_IMAGE) != 0) {
//
//				x = _modules_x[module] & 0xFF;
//				y = _modules_y[module] & 0xFF;
//				sizeX = _modules_w[module] & 0xFF;
//				sizeY = _modules_h[module] & 0xFF;
//			}
//			// if (posY < cy) { y = cy - posY; sizeY -= y; posY = cy; if (sizeY
//			// <= 0) return; }
//			// if (posY+sizeY > cy+ch) { sizeY = cy+ch - posY; if (sizeY <= 0)
//			// return; }
//
//			// Draw...
//			if ((flags & FLAG_FLIP_X) != 0) {
//				if ((flags & FLAG_FLIP_Y) != 0) {
//					// TODO: clip...
//					g.drawRegion(img, x, y, sizeX, sizeY, Sprite.TRANS_ROT180,
//							posX, posY, 0);
//				} else {
//					// if (posX < cx) { sizeX -= cx - posX; posX = cx; }
//					// if (posX+sizeX > cx+cw) { x = cx+cw - posX; sizeX -= x; }
//					g.drawRegion(img, x, y, sizeX, sizeY, Sprite.TRANS_MIRROR,
//							posX, posY, 0);
//				}
//			} else if ((flags & FLAG_FLIP_Y) != 0) {
//				// TODO: clip...
//				g.drawRegion(img, x, y, sizeX, sizeY,
//						Sprite.TRANS_MIRROR_ROT180, posX, posY, 0);
//			} else {
//				// if (posX < cx) { x = cx - posX; sizeX -= x; posX = cx; }
//				// if (posX+sizeX > cx+cw) { sizeX = cx+cw - posX; }
//
//				g.drawRegion(img, x, y, sizeX, sizeY, Sprite.TRANS_NONE, posX,
//						posY, 0);
//
//			}
//			img = null;
//		}
//
//		// System.out.println("...PaintModule(_crt_pal = "+_crt_pal+")");
//		// System.out.println("...PaintModule(g, "+module+", "+posX+", "+posY+",
//		// 0x"+Integer.toHexString(flags)+")");
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	int newSizeX;
	int newSizeY;

//	public byte[] ScaleImageI256(int module, int factor1, int factor2) {
//		byte[] buf_img_data = new byte[5000]; // you can reuse one static
//		// buffer
//		if (_modules_data == null || _modules_data_off == null)
//			return null;
//
//		int m = (module << 1);
//		int sizeX = _modules_w[m] & 0xFF;
//		int sizeY = _modules_h[m] & 0xFF;
//
//		newSizeX = factor1 * sizeX / factor2;
//		newSizeY = factor1 * sizeY / factor2;
//
//		byte[] image = _modules_data;
//		int si = _modules_data_off[module];
//		int di = 0;
//		int ds = newSizeX * newSizeY;
//		int i = 0, j = 0;
//		for (i = 0; i < newSizeY; i++) {
//			int i0 = ((i * sizeY) / newSizeY) * sizeX;
//			int i1 = i * newSizeX;
//			for (j = 0; j < newSizeX; j++)
//				buf_img_data[j + i1] = image[si + ((j * sizeX) / newSizeX) + i0];
//		}
//		return buf_img_data;
//	}
//
//	// //////////////////////////////////////////////////////////////////////////////////////////////////
//
//	int[] DecodeImage(int module, int flags)
//
//	{
//		// System.out.println("DecodeImage("+module+",
//		// 0x"+Integer.toHexString(flags)+")...");
//
//		if (_modules_data == null || _modules_data_off == null)
//			return null;
//
//		int sizeX = _modules_w[module] & 0xFF;
//		int sizeY = _modules_h[module] & 0xFF;
//		// if (sizeX <= 0 || sizeY <= 0) return null;
//
//		if (temp == null) {
//			temp = new int[config.k_sprite_temp_size];
//		}
//		// if (DEF.bErr) {
//		// if (sizeX * sizeY > temp.length) {
//		// System.out.println("ERROR: sizeX x sizeY > temp.length ("
//		// + sizeX + " x " + sizeY + " = " + sizeX * sizeY + " > "
//		// + temp.length + ") !!!");
//		// return null;
//		// }
//		// }
//
//		int[] img_data = temp;
//
//		// if (flags == 1)
//		// img_data = new short[sizeX * sizeY + 7];
//
//		// Choose palette...
//
//		// if(s_modifyPaletteAlphaModuleId == module) {
//		// ModifyPaletteAlpha(_crt_pal, s_modifyPaletteAlphaValue);
//		// }
//
//		int[] pal = _pal[_crt_pal];
//
//		if (pal == null)
//			return null;
//
//		// Build displayable...
//		byte[] image = _modules_data;
//		int si = _modules_data_off[module];
//		int di = 0;
//		int ds = sizeX * sizeY;
//
//		// HINT: Sort these encoders regarding how often are used by your game!
//		if ((_data_format == ENCODE_FORMAT_I64RLE) && USE_ENCODE_FORMAT_I64RLE) {
//			// variable RLE compression, max 64 colors...
//
//			if (_drawAlp) {
//				while (di < ds) {
//					int c = image[si++] & 0xFF;
//
//					int clr = pal[c & _i64rle_color_mask];
//
//					c >>= _i64rle_color_bits;
//					while (c-- >= 0)
//						img_data[di++] = (clr & 0xAAFFFFFF);
//				}
//			} else if (_drawTransparency) {
//
//				while (di < ds) {
//					int c = image[si++] & 0xFF;
//
//					int clr = pal[c & _i64rle_color_mask];
//
//					c >>= _i64rle_color_bits;
//					while (c-- >= 0)
//						if (clr != _transparenceColor)
//							img_data[di++] = ((clr & 0X00FFFF) | (_alphaStep << 16))
//									| (_alphaStep << 24);
//						else
//							img_data[di++] = clr;
//				}
//			} else if (_drawAlpDep) {
//				while (di < ds) {
//					int c = image[si++] & 0xFF;
//					int clr = pal[c & _i64rle_color_mask];
//
//					c >>= _i64rle_color_bits;
//					while (c-- >= 0)
//						if (clr != _transparenceColor)
//							img_data[di++] = ((clr & 0XFFFFFF) | 0xFF)
//									| (_alphaStep << 24);
//						else
//							img_data[di++] = clr;
//				}
//			} else {
//
//				while (di < ds) {
//					int c = image[si++] & 0xFF;
//
//					int clr = pal[c & _i64rle_color_mask];
//
//					c >>= _i64rle_color_bits;
//					while (c-- >= 0)
//						img_data[di++] = clr;
//				}
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I127RLE)
//				&& USE_ENCODE_FORMAT_I127RLE) {
//			// fixed RLE compression, max 127 colors...
//			while (di < ds) {
//				int c = image[si++] & 0xFF;
//				if (c > 127) {
//					int c2 = image[si++] & 0xFF;
//
//					int clr = pal[c2];
//
//					c -= 128;
//					while (c-- > 0)
//						img_data[di++] = clr;
//				} else
//					img_data[di++] = pal[c];
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I256RLE)
//				&& USE_ENCODE_FORMAT_I256RLE) {
//			if (_drawRedAlp) {
//				while (di < ds) {
//					int c = image[si++] & 0xFF;
//					if (c > 127) {
//						c -= 128;
//						while (c-- > 0) {
//							int cl = pal[image[si++] & 0xFF];
//							if (cl != _transparenceColor) {
//								img_data[di++] = ((cl & 0X00FFFF) | (_alphaStep << 16))
//										| (_alphaStep << 24);
//
//							} else
//								img_data[di++] = cl;
//						}
//					} else {
//
//						int clr = pal[image[si++] & 0xFF];
//						while (c-- > 0) {
//							if (clr != _transparenceColor)
//								img_data[di++] = ((clr & 0X00FFFF) | (_alphaStep << 16))
//										| (_alphaStep << 24);
//							else
//								img_data[di++] = clr;
//						}
//					}
//				}
//			} else if (_drawTransparency) {
//				while (di < ds) {
//					int c = image[si++] & 0xFF;
//					if (c > 127) {
//						c -= 128;
//						while (c-- > 0) {
//							int cl = pal[image[si++] & 0xFF];
//							if (cl != _transparenceColor) {
//								img_data[di++] = ((cl & 0XFFFFFF) | _alphaStep)
//										| (_alphaStep << 24);
//
//							} else
//								img_data[di++] = cl;
//						}
//					} else {
//
//						int clr = pal[image[si++] & 0xFF];
//						while (c-- > 0) {
//							if (clr != _transparenceColor)
//								img_data[di++] = ((clr & 0XFFFFFF) | _alphaStep)
//										| (_alphaStep << 24);
//							else
//								img_data[di++] = clr;
//						}
//					}
//				}
//			} else {
//				// fixed RLE compression, max 256 colors...
//				while (di < ds) {
//					int c = image[si++] & 0xFF;
//					if (c > 127) {
//						c -= 128;
//						while (c-- > 0)
//							img_data[di++] = pal[image[si++] & 0xFF];
//					} else {
//
//						int clr = pal[image[si++] & 0xFF];
//
//						while (c-- > 0)
//							img_data[di++] = clr;
//					}
//				}
//			}
//
//		} else if ((_data_format == ENCODE_FORMAT_I16) && USE_ENCODE_FORMAT_I16) {
//			// 2 pixels/byte, max 16 colors...
//			while (di < ds) {
//				img_data[di++] = pal[(image[si] >> 4) & 0x0F];
//				img_data[di++] = pal[(image[si]) & 0x0F];
//				si++;
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I4) && USE_ENCODE_FORMAT_I4) {
//			// 4 pixels/byte, max 4 colors...
//			while (di < ds) {
//				img_data[di++] = pal[(image[si] >> 6) & 0x03];
//				img_data[di++] = pal[(image[si] >> 4) & 0x03];
//				img_data[di++] = pal[(image[si] >> 2) & 0x03];
//				img_data[di++] = pal[(image[si]) & 0x03];
//				si++;
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I2) && USE_ENCODE_FORMAT_I2) {
//			// 8 pixels/byte, max 2 colors...
//			while (di < ds) {
//				img_data[di++] = pal[(image[si] >> 7) & 0x01];
//				img_data[di++] = pal[(image[si] >> 6) & 0x01];
//				img_data[di++] = pal[(image[si] >> 5) & 0x01];
//				img_data[di++] = pal[(image[si] >> 4) & 0x01];
//				img_data[di++] = pal[(image[si] >> 3) & 0x01];
//				img_data[di++] = pal[(image[si] >> 2) & 0x01];
//				img_data[di++] = pal[(image[si] >> 1) & 0x01];
//				img_data[di++] = pal[(image[si]) & 0x01];
//				si++;
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I256)
//				&& USE_ENCODE_FORMAT_I256) {
//			// 1 pixel/byte, max 256 colors...
//			while (di < ds)
//				img_data[di++] = pal[image[si++] & 0xFF];
//		}
//		if (m_precalImagesPoolID < 0) {
//			if ((flags & FLAG_FLIP_X) != 0) {
//
//				int temp;
//
//				for (int x1 = 0, x2 = sizeX - 1; x1 < x2; x1++, x2--) {
//					for (int off = 0; off < ds; off += sizeX) {
//						temp = img_data[off + x1];
//						img_data[off + x1] = img_data[off + x2];
//						img_data[off + x2] = temp;
//					}
//				}
//			}
//
//			if ((flags & FLAG_FLIP_Y) != 0) {
//
//				int temp;
//
//				for (int off1 = 0, off2 = (sizeY - 1) * sizeX; off1 < off2; off1 += sizeX, off2 -= sizeX) {
//					for (int x = 0; x < sizeX; x++) {
//						temp = img_data[off1 + x];
//						img_data[off1 + x] = img_data[off2 + x];
//						img_data[off2 + x] = temp;
//					}
//				}
//			}
//		}
//		// if(s_modifyPaletteAlphaModuleId == module) {
//		// ResetPaletteAlpha(_crt_pal);
//		// s_modifyPaletteAlphaModuleId = -1;
//		// }
//		// System.out.println("...DecodeImage("+module+",
//		// 0x"+Integer.toHexString(flags)+")");
//		return img_data;
//	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

//	short[] DecodeImage_short(int module, int flags) {
//		// System.out.println("DecodeImage("+module+",
//		// 0x"+Integer.toHexString(flags)+")...");
//
//		if (_modules_data == null || _modules_data_off == null)
//			return null;
//
//		int sizeX = _modules_w[module] & 0xFF;
//		int sizeY = _modules_h[module] & 0xFF;
//		// if (sizeX <= 0 || sizeY <= 0) return null;
//
//		if (temp_short == null) {
//			temp_short = new short[config.k_sprite_temp_size];
//		}
//		// if (DEF.bErr) {
//		// if (sizeX * sizeY > temp_short.length) {
//		// System.out.println("ERROR: sizeX x sizeY > temp.length ("
//		// + sizeX + " x " + sizeY + " = " + sizeX * sizeY + " > "
//		// + temp_short.length + ") !!!");
//		// return null;
//		// }
//		// }
//
//		short[] img_data = temp_short;
//
//		// if (flags == 1)
//		// img_data = new short[sizeX * sizeY + 7];
//
//		// Choose palette...
//
//		short[] pal = _pal_short[_crt_pal];
//
//		if (pal == null)
//			return null;
//
//		// Build displayable...
//		byte[] image = _modules_data;
//		int si = _modules_data_off[module];
//		int di = 0;
//		int ds = sizeX * sizeY;
//
//		// HINT: Sort these encoders regarding how often are used by your game!
//		if ((_data_format == ENCODE_FORMAT_I64RLE) && USE_ENCODE_FORMAT_I64RLE) {
//			// variable RLE compression, max 64 colors...
//			while (di < ds) {
//				int c = image[si++] & 0xFF;
//
//				short clr = pal[c & _i64rle_color_mask];
//
//				c >>= _i64rle_color_bits;
//				while (c-- >= 0)
//					img_data[di++] = clr;
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I127RLE)
//				&& USE_ENCODE_FORMAT_I127RLE) {
//			// fixed RLE compression, max 127 colors...
//			while (di < ds) {
//				int c = image[si++] & 0xFF;
//				if (c > 127) {
//					int c2 = image[si++] & 0xFF;
//
//					short clr = pal[c2];
//
//					c -= 128;
//					while (c-- > 0)
//						img_data[di++] = clr;
//				} else
//					img_data[di++] = pal[c];
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I256RLE)
//				&& USE_ENCODE_FORMAT_I256RLE) {
//			// fixed RLE compression, max 256 colors...
//			while (di < ds) {
//				int c = image[si++] & 0xFF;
//				if (c > 127) {
//					c -= 128;
//					while (c-- > 0)
//						img_data[di++] = pal[image[si++] & 0xFF];
//				} else {
//
//					short clr = pal[image[si++] & 0xFF];
//
//					while (c-- > 0)
//						img_data[di++] = clr;
//				}
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I16) && USE_ENCODE_FORMAT_I16) {
//			// 2 pixels/byte, max 16 colors...
//			while (di < ds) {
//				img_data[di++] = pal[(image[si] >> 4) & 0x0F];
//				img_data[di++] = pal[(image[si]) & 0x0F];
//				si++;
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I4) && USE_ENCODE_FORMAT_I4) {
//			// 4 pixels/byte, max 4 colors...
//			while (di < ds) {
//				img_data[di++] = pal[(image[si] >> 6) & 0x03];
//				img_data[di++] = pal[(image[si] >> 4) & 0x03];
//				img_data[di++] = pal[(image[si] >> 2) & 0x03];
//				img_data[di++] = pal[(image[si]) & 0x03];
//				si++;
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I2) && USE_ENCODE_FORMAT_I2) {
//			// 8 pixels/byte, max 2 colors...
//			while (di < ds) {
//				img_data[di++] = pal[(image[si] >> 7) & 0x01];
//				img_data[di++] = pal[(image[si] >> 6) & 0x01];
//				img_data[di++] = pal[(image[si] >> 5) & 0x01];
//				img_data[di++] = pal[(image[si] >> 4) & 0x01];
//				img_data[di++] = pal[(image[si] >> 3) & 0x01];
//				img_data[di++] = pal[(image[si] >> 2) & 0x01];
//				img_data[di++] = pal[(image[si] >> 1) & 0x01];
//				img_data[di++] = pal[(image[si]) & 0x01];
//				si++;
//			}
//		} else if ((_data_format == ENCODE_FORMAT_I256)
//				&& USE_ENCODE_FORMAT_I256) {
//			// 1 pixel/byte, max 256 colors...
//			while (di < ds)
//				img_data[di++] = pal[image[si++] & 0xFF];
//		}
//
//		if ((flags & FLAG_FLIP_X) != 0) {
//
//			short temp;
//
//			for (int x1 = 0, x2 = sizeX - 1; x1 < x2; x1++, x2--) {
//				for (int off = 0; off < ds; off += sizeX) {
//					temp = img_data[off + x1];
//					img_data[off + x1] = img_data[off + x2];
//					img_data[off + x2] = temp;
//				}
//			}
//		}
//
//		if ((flags & FLAG_FLIP_Y) != 0) {
//
//			short temp;
//
//			for (int off1 = 0, off2 = (sizeY - 1) * sizeX; off1 < off2; off1 += sizeX, off2 -= sizeX) {
//				for (int x = 0; x < sizeX; x++) {
//					temp = img_data[off1 + x];
//					img_data[off1 + x] = img_data[off2 + x];
//					img_data[off2 + x] = temp;
//				}
//			}
//		}
//
//		// System.out.println("...DecodeImage("+module+",
//		// 0x"+Integer.toHexString(flags)+")");
//		return img_data;
//	}

	// int[] DecodeImageForBlendTile(int module, int flags)
	// {
	// // System.out.println("DecodeImage("+module+",
	// 0x"+Integer.toHexString(flags)+")...");
	//
	// if (_modules_data == null ||
	// _modules_data_off == null) return null;
	//
	// int sizeX = _modules_w[module] & 0xFF;
	// int sizeY = _modules_h[module] & 0xFF;
	// // if (sizeX <= 0 || sizeY <= 0) return null;
	//
	// if (DEF.bErr)
	// {
	// if (sizeX * sizeY > temp.length)
	// {
	// System.out.println("ERROR: sizeX x sizeY > temp.length ("+sizeX+" x
	// "+sizeY+" = "+sizeX * sizeY+" > "+temp.length+") !!!");
	// return null;
	// }
	// }
	//
	// int[] img_data = temp;
	// // if (flags == 1)
	// // img_data = new short[sizeX * sizeY + 7];
	//
	// // Choose palette...
	// int[] pal = _pal[_crt_pal];
	// if (pal == null) return null;
	//
	// // Build displayable...
	// byte[] image = _modules_data;
	// int si = _modules_data_off[module];
	// int di = 0;
	// int ds = sizeX * sizeY;
	//
	// // HINT: Sort these encoders regarding how often are used by your game!
	// if ((_data_format == ENCODE_FORMAT_I256RLE) && USE_ENCODE_FORMAT_I256RLE)
	// {
	// // fixed RLE compression, max 256 colors...
	// try
	// {
	// while (di < ds) {
	// int c = image[si++] & 0xFF;
	// if (c > 127) {
	// c -= 128;
	// while (c-- > 0)
	// img_data[di++] = image[si++] & 0xFF;
	// }
	// else {
	// int clr = image[si++] & 0xFF;
	// while (c-- > 0)
	// img_data[di++] = clr;
	// }
	// }
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	//
	// if ((flags & FLAG_FLIP_X) != 0)
	// {
	// int temp;
	// for (int x1 = 0, x2 = sizeX - 1; x1 < x2; x1++, x2--)
	// {
	// for (int off = 0; off < ds; off += sizeX)
	// {
	// temp = img_data[off + x1];
	// img_data[off + x1] = img_data[off + x2];
	// img_data[off + x2] = temp;
	// }
	// }
	// }
	//
	// if ((flags & FLAG_FLIP_Y) != 0)
	// {
	// int temp;
	// for (int off1 = 0, off2 = (sizeY - 1) * sizeX; off1 < off2; off1 +=
	// sizeX, off2 -= sizeX)
	// {
	// for (int x = 0; x < sizeX; x++)
	// {
	// temp = img_data[off1 + x];
	// img_data[off1 + x] = img_data[off2 + x];
	// img_data[off2 + x] = temp;
	// }
	// }
	// }
	//
	// // System.out.println("...DecodeImage("+module+",
	// 0x"+Integer.toHexString(flags)+")");
	// return img_data;
	// }
	// NOT_HAVE_LIGHT_TILE
	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public void SetCurrentPalette(int pal) {
		_crt_pal = pal;
	}

	int GetCurrentPalette() {
		return _crt_pal;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * void SetColor(int index, short color) { // _font_color =
	 * _pal[_crt_pal][index] = color; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * //////////////////////////////////////////////////////////////////////////
	 * //////////////////////////
	 * 
	 * void SetColor(int index, int color) { // _font_color =
	 * _pal[_crt_pal][index] = (short)(((((color & 0xFF000000)>>24) & 0xF0)<<8)
	 * | ((((color & 0x00FF0000)>>16) & 0xF0)<<4) | ((((color & 0x0000FF00)>>8 )
	 * & 0xF0) ) | ((((color & 0x000000FF) ) & 0xF0)>>4)); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * //////////////////////////////////////////////////////////////////////////
	 * //////////////////////////
	 * 
	 * void SetColor(int index, int a, int r, int g, int b) { // _font_color =
	 * _pal[_crt_pal][index] = (short)(((a & 0xF0)<<8) | ((r & 0xF0)<<4) | ((g &
	 * 0xF0) ) | ((b & 0xF0)>>4)); }
	 */
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// // Draw String System...
	//
	// private void DRAW_STRINGS_SYSTEM___() {}
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// // _modules_w[0] -> w -> width of the space character (' ')
	// // _modules_h[0] -> h -> height of a text line
	// // _fmodules[0*4+1] -> ox -> space between two adiacent chars
	// // _fmodules[0*4+2] -> oy -> base line offset
	//
	// // Used to gather dimensions of a string...
	// // (call UpdateStringSize() to update these values)
	// static int _text_w;
	// static int _text_h;
	//
	// // Maps an ASCII char to a sprite FModule...
	// byte[] _map_char;// = new byte[256]; NEEDS TO BE LOADED FROM RESOURCES
	// !!!
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// // Space between two lines of text...
	//
	//	
	//		
	// private int _line_spacing = 2;
	//		
	//	
	// int GetLineSpacing() { return _line_spacing; }
	// void SetLineSpacing(int spacing) { _line_spacing = spacing; }
	// void SetLineSpacingToDefault() { _line_spacing = ((_modules_h[0]&0xFF) >>
	// 1); }
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// static int _index1 = -1;
	// static int _index2 = -1;
	//
	// static final String TEXT = "
	// !\"#$%&'*+,_./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ()-\u00c4\u00d6\u00dc\u00df\u00a1\u00bf\u00c1\u00c0\u00c3\u00c8\u00c9\u00ca\u00ce\u00cf\u00cd\u00d3\u00d4\u00d5\u00da\u00d9\u00d1\u00c7\u00D2\u00c2\u00cc\u00a9";
	//
	// static final int TEXT_0 = 14;//TEXT.indexOf('0');
	// static final int TEXT_A = 31;//TEXT.indexOf('A');
	// static final int TEXT_LEN = TEXT.length();
	//
	// private static int GetCharOffset(char ch) {
	// int index;
	// if (ch >= 'A' && ch <= 'Z') {
	// index = ch - 'A' + TEXT_A;
	// }
	// else if (ch >= 'a' && ch <= 'z') {
	// index = ch - 'a' + TEXT_A;
	// }
	// else if (ch >= '0' && ch <= '9') {
	// index = ch - '0' + TEXT_0;
	// }
	// else {
	// index = TEXT.indexOf(ch);
	// }
	// if (index == -1)
	// index = TEXT.indexOf(ch - 0x20);
	// return index;
	// }
	//
	//
	// static void SetSubString(int i1, int i2)
	// {
	// _index1 = i1;
	// _index2 = i2;
	// }
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// void UpdateStringSize(String s)
	// {
	// // if (DEF.bASSERT) DBG.ASSERT(_map_char != null, "_map_char != null");
	//
	// _text_w = 0;
	// _text_h = (_modules_h[1]&0xFF);
	// int tw = 0;
	//
	// int index1 = ((_index1 >= 0) ? _index1 : 0);
	// int index2 = ((_index2 >= 0) ? _index2 : s.length());
	//
	// for (int i = index1; i < index2; i++) {
	// char c = s.charAt(i);
	// if (c == '{' || c == '}')
	// continue;
	// else if( c == '~' )
	// {
	// i++;
	// tw += _modules_w[0] * 2;
	// continue;
	// }
	//        
	// int off = GetCharOffset(c);
	// if (off == -1) {
	// tw += (_modules_w[0] & 0xFF);
	// }
	// else {
	// int m = (_fmodules[off << 2] & 0xFF);
	// tw += (_modules_w[m] & 0xFF) + _fmodules[1];
	// }
	// }
	// if (tw > _text_w) _text_w = tw;
	// //if (_text_w > 0) _text_w -= _fmodules[1];
	// }
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// void DrawString(Graphics g, String s, int x, int y, int anchor)
	// {
	// if (y < -20 || y > cGame.SCREEN_HEIGHT)
	// {
	// return;//no need to draw the string that out of screen
	// }
	// // if (DEF.bASSERT) DBG.ASSERT(_map_char != null, "_map_char != null");
	// s = s.toUpperCase();
	// //y -= _fmodules[2];
	//
	// if ((anchor & (Graphics.RIGHT | Graphics.HCENTER | Graphics.BOTTOM |
	// Graphics.VCENTER)) != 0)
	// {
	// UpdateStringSize(s);
	// if ((anchor & Graphics.RIGHT) != 0) x -= _text_w;
	// else if ((anchor & Graphics.HCENTER) != 0) x -= _text_w>>1;
	// if ((anchor & Graphics.BOTTOM) != 0) y -= _text_h;
	// else if ((anchor & Graphics.VCENTER) != 0) y -= _text_h>>1;
	// }
	//
	// int xx = x;
	// int yy = y;
	//
	// //int old_pal = _crt_pal;
	//
	// int index1 = ((_index1 >= 0) ? _index1 : 0);
	// int index2 = ((_index2 >= 0) ? _index2 : s.length());
	//
	// for (int i = index1; i < index2; i++)
	// {
	// char c = s.charAt(i);
	// if( c == '{' )
	// {
	// SetCurrentPalette(1);
	// continue;
	// }
	// else if( c == '}' )
	// {
	// SetCurrentPalette(0);
	// continue;
	// }
	// else if( c == '~' )//draw potion and stone in help text
	// {
	// int index = s.charAt(++i) - '0';
	//
	// if( index == 1 )
	// cGame.s_sprites[DATA.SPRITE_JARITEM].PaintAFrame(g,ANIM.JARITEM_HEALTHPOTION
	// , 0 , xx , yy + (_modules_h[1] & 0xFF) - 5 , 0 , 0, 0);
	// else if( index == 2 )
	// cGame.s_sprites[DATA.SPRITE_JARITEM].PaintAFrame(g,ANIM.JARITEM_ALCHEMICAL_STONE
	// , 0 , xx , yy + (_modules_h[1] & 0xFF) + 3 , 0 , 0, 0);
	//
	//
	// xx += _modules_w[0] * 3;
	//
	// continue;
	// }
	//                        
	// int off = GetCharOffset(c);
	// if (off == -1) {
	// xx += (_modules_w[0] & 0xFF);
	// }
	// else {
	// PaintFModule(g, 0, off, xx, yy, 0, 0, 0);
	// int m = (_fmodules[off << 2] & 0xFF);
	//						
	// xx += (_modules_w[m] & 0xFF) + _fmodules[1];
	//						
	// }
	// }
	//
	// //_crt_pal = old_pal;
	// }
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// int DrawPage(Graphics g, String s, int x, int y, int anchor)
	// {
	// s='}'+s;
	// int char_num = DEF.CHAR_NUM_NORMAL;
	// if( cGame.s_gameState == STATE.STORY )
	// char_num = DEF.CHAR_NUM_STROY;
	//		
	// else if( cGame.s_gameState == STATE.DIALOG
	// &&(cGame.s_dialogType == cGame.DIALOG_TYPE_DIALOG_MENU ||
	// cGame.s_dialogType == cGame.DIALOG_TYPE_DIALOG_INGAME ||
	// cGame.s_dialogType == cGame.DIALOG_TYPE_DIALOG_ENDLEVEL) )
	//		
	// char_num = DEF.CHAR_NUM_DIALOG;
	// // Count lines...
	// int lines = 0;
	// int num = 0;
	// int len = s.length();
	// int[] off = new int[200];
	// for (int i = 0; i < len; i++) {
	// if (s.charAt(i) == '^'&&num <=char_num) {
	// off[lines++] = i;
	// num = 0;
	// }
	// else if (char_num > 0 && (s.charAt(i) == '^'||s.charAt(i) == ' ' || i ==
	// len - 1) && num > char_num) {
	// while( s.charAt(--i) != ' ' && i > (lines > 0 ? off[lines-1] : 0))
	// ;
	// if( i == (lines > 0 ? off[lines-1] : 0) )
	// {
	// i = (lines > 0 ? off[lines-1] : 0) + num;
	//
	// }
	// off[lines++] = i;
	// num = 0;
	// }
	// else
	// num++;
	// }
	// off[lines++] = len;
	//    
	// int th = _line_spacing + (_modules_h[1] & 0xFF);
	//            
	//
	// if( cGame.s_gameState == STATE.STORY )
	// th += 3;
	//				
	// if ( (anchor & Graphics.BOTTOM) != 0) y -= (th * (lines - 1));
	// else if ( (anchor & Graphics.VCENTER) != 0) y -= (th * (lines - 1)) >> 1;
	//    
	// // Draw each line...
	// for (int j = 0; j < lines; j++) {
	// _index1 = (j > 0) ? off[j - 1] + 1 : 0;
	// _index2 = off[j];
	// DrawString(g, s, x, y + j * th, anchor);
	// }
	//    
	// // Disable substring...
	// _index1 = -1;
	// _index2 = -1;
	//    
	// return y + lines * th;
	// }
	//	
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// private void ___DRAW_STRINGS_SYSTEM() {}
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// Palette generation based on other palette...
	// -1 - original colors
	// 0 - invisible (the sprite will be hidden)
	// 1 - red-yellow
	// 2 - blue-cyan
	// 3 - green
	// 4 - grey
	static int[] GenPalette(int type, int pal[]) {
		if (type < 0)
			return pal; // original
		if (type == 0)
			return null; // invisible

		int[] new_pal = new int[pal.length];
		switch (type) {
		/*
		 * case 1: // red - yellow for (int i = 0; i < pal.length; i++)
		 * new_pal[i] = (pal[i] | 0x00FF3300) & 0xFFFFFF00; break;
		 * 
		 * case 2: // blue - cyan for (int i = 0; i < pal.length; i++)
		 * new_pal[i] = (pal[i] | 0x000033FF) & 0xFF00FFFF; break;
		 * 
		 * case 3: // green for (int i = 0; i < pal.length; i++) new_pal[i] =
		 * (pal[i] | 0x00000000) & 0xFF00FF00; break;
		 */
		case 4: // grey (desaturate)
			for (int i = 0; i < pal.length; i++) {
				int a = (pal[i] & 0xFF000000);
				int r = (pal[i] & 0x00FF0000) >> 16;
				int g = (pal[i] & 0x0000FF00) >> 8;
				int b = (pal[i] & 0x000000FF);
				int l = ((r + b + g) / 3) & 0x000000FF;
				new_pal[i] = ((l << 16) | (l << 8) | l | a);
			}
			break;
		/*
		 * case 5: // blend with black 50% for (int i = 0; i < pal.length; i++)
		 * { int a = (pal[i] & 0xFF000000); int r = (pal[i] & 0x00FC0000) >> 2;
		 * int g = (pal[i] & 0x0000FC00) >> 2; int b = (pal[i] & 0x000000FC) >>
		 * 2; new_pal[i] = (a | r | g | b); } break;
		 */
		}

		return new_pal;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// Sprite lib...
	/*
	 * static ASprite[] _sprites;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * //////////////////////////////////////////////////////////////////////////
	 * //////////////////////////
	 * 
	 * static ASprite Lib_GetSprite(int index) { if (index < 0 || index >=
	 * _sprites.length) return null; return _sprites[index]; }
	 */
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	public static int DEFAULT_ALPHA = 0xFF;
	static int s_modifyPaletteAlphaModuleId;
	static int s_modifyPaletteAlphaValue;

	void ModifyPalette(int palNb, int color) {
		_alpha = true;
		{
			color = color & 0x00FFFFFF;
			for (int i = 0; i < _pal[palNb].length; i++) {
				if ((_pal[palNb][i] & 0xFFFFFF) != 0xFF00FF)
					_pal[palNb][i] = (((_pal[palNb][i]) << 24) | color);
			}
		}
	}

	public void ModifyPaletteAlpha(int palNb, int alpha) {

		alpha = alpha & 0xFF;
		for (int i = 0; i < _pal[palNb].length; i++) {
			if ((_pal[palNb][i] & 0xFFFFFF) != 0xFF00FF
					&& (_pal[palNb][i] >> 24) != 0)
				_pal[palNb][i] = (((alpha & 0xFF) << 24) | (_pal[palNb][i] & 0x00FFFFFF));
		}

	}

	void ResetPaletteAlpha(int palNb) {
		ModifyPaletteAlpha(palNb, DEFAULT_ALPHA);
	}

	public final static int POOLS_COUNT = 3;

	public final static int SPRITE_POOL = 0;
	public final static int TILE_POOL = 1;
	public final static int MENU_POOL = 2;

	public final static int ANIM_SIZE = 150;
	public final static int TILE_POOL_SIZE = 60;
	public final static int MENU_POOL_SIZE = 20;
	/**
	 * table stores image pointers of module images. a null pointer menas the
	 * corresonding moudle is not cached.
	 */

	static Image[][] s_precalcImages = new Image[POOLS_COUNT][];

	/**
	 * table stores the indices of module images being cahced.
	 */
	static short[][] s_precalcStack = new short[POOLS_COUNT][];

	/**
	 * the cound of module images
	 */
	static int[] s_precalcImagesCount = new int[POOLS_COUNT];

	/**
	 * the loop cursor
	 */
	static int[] s_precalcStackIndex = new int[POOLS_COUNT];

	/**
	 * the pool size
	 */
	static int[] s_precalcStackMax = new int[POOLS_COUNT];

//	public static void SetPrecalcImagePoolSize(int poolID, int size) {
//		if (size < 0) {
//			size = s_precalcImagesCount[poolID];
//		}
//
//		s_precalcStackMax[poolID] = size;
//
//		// s_precalcStackMax must > 0
//		s_precalcStack[poolID] = new short[size];
//
//		for (int i = 0; i < s_precalcStack[poolID].length; ++i) {
//			s_precalcStack[poolID][i] = -1;
//		}
//
//		// s_precalcImagesCount must > 0
//
//		s_precalcImages[poolID] = new Image[s_precalcImagesCount[poolID]];
//
//	}

//	public static void ResetPrecalcImagePool(int poolID) {
//		s_precalcStack[poolID] = null;
//		s_precalcStackIndex[poolID] = 0;
//		s_precalcStackMax[poolID] = 0;
//		s_precalcImages[poolID] = null;
//		s_precalcImagesCount[poolID] = 0;
//	}

	/** first precal image index of palette 0 */
	int m_precalImagesOffset;

	/** which pool the cached images of this gli will be created in */
	int m_precalImagesPoolID = -1;

	void EnablePrecalcImage(int poolID) {
		m_precalImagesOffset = s_precalcImagesCount[poolID];
		s_precalcImagesCount[poolID] += _palettes * _nModules;
		m_precalImagesPoolID = poolID;
	}

} // class ASprite

// //////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////

// #switch (Draw_API)
// #{
// #case "MIDP10":
// #include "..\Engine\MIDP10\AuroraSprite.h"
// #break
// #case "Nokia":
// #include "..\Engine\Nokia\AuroraSprite.h"
// #break
// #default:
// #include "..\Engine\MIDP20\AuroraSprite.h"
// #break
// #}
