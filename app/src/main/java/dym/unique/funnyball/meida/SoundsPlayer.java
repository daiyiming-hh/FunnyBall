package dym.unique.funnyball.meida;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

import dym.unique.funnyball.R;
import dym.unique.funnyball.sharedperference.GameSharedPreference;

/**
 * Created by daiyiming on 2016/4/7.
 */
public class SoundsPlayer {

    private final static int SOUND_KEY_TOUCH = 0;
    private final static int SOUND_KEY_LOST_LIFE = 1;
    private final static int SOUND_KEY_GAME_OVER = 2;

    private static SoundPool mSoundPool = null;
    private static Map<Integer, Integer> mSoundsMap = new HashMap<>();
    private static boolean mIsCanPlaySounds = true;

    private SoundsPlayer() {}

    public static void init(Context context) {
        mSoundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 5);
        mSoundsMap.put(SOUND_KEY_TOUCH, mSoundPool.load(context, R.raw.touch, 1));
        mSoundsMap.put(SOUND_KEY_LOST_LIFE, mSoundPool.load(context, R.raw.lost_life, 1));
        mSoundsMap.put(SOUND_KEY_GAME_OVER, mSoundPool.load(context, R.raw.game_over, 1));

        mIsCanPlaySounds = GameSharedPreference.getIsCanPlaySounds();
    }

    public static void setIsCanPlaySounds(boolean isCanPlaySounds) {
        if (mIsCanPlaySounds != isCanPlaySounds) {
            mIsCanPlaySounds = isCanPlaySounds;
            GameSharedPreference.saveIsCanPlaySounds(mIsCanPlaySounds);
        }
    }

    public static boolean getIsCanPlaySounds() {
        return mIsCanPlaySounds;
    }

    public static void playTouchSound() {
        if (mIsCanPlaySounds) {
            mSoundPool.play(mSoundsMap.get(SOUND_KEY_TOUCH), 1, 1, 0, 0, 1);
        }
    }

    public static void playLostLifeSound() {
        if (mIsCanPlaySounds) {
            mSoundPool.play(mSoundsMap.get(SOUND_KEY_LOST_LIFE), 1, 1, 0, 0, 1);
        }
    }

    public static void playGameOverSound() {
        if (mIsCanPlaySounds) {
            mSoundPool.play(mSoundsMap.get(SOUND_KEY_GAME_OVER), 1, 1, 0, 0, 1);
        }
    }

    public static void release() {
        mSoundPool.release();
    }

}
