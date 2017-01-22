package io.github.anotherme17.commonrvadapter.animation;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * extends from {@link BaseItemAnimator} <br/>
 * <p>
 * To achieve RecyclerView Item animation effects you need to rewrite those method
 * <li> Add Item Animation {@link #animateAdd(RecyclerView.ViewHolder)} <br/>
 * {@link #animationAddImpl(RecyclerView.ViewHolder)}</li>
 * <li> Remove Item Animation {@link #animateRemove(RecyclerView.ViewHolder)} <br/>
 * {@link #animateRemoveImpl(RecyclerView.ViewHolder)}</li>
 * <li> Move Item Animation {@link #animateMove(RecyclerView.ViewHolder, int, int, int, int)} <br/>
 * {@link #animateMoveImpl(RecyclerView.ViewHolder, int, int, int, int)}</li>
 * <li> Change Item Animation
 * {@link #animateChange(RecyclerView.ViewHolder, RecyclerView.ViewHolder, int, int, int, int)} <br/>
 * {@link #animateChangeImpl(ChangeInfo)}</li>
 * </P>
 * <p>
 * <p>
 * You can also set Animation Duration With Method Like {@link #setAddDuration(long)} ,
 * {@link #setChangeDuration(long)} ,{@link #setMoveDuration(long)} ,
 * {@link #setRemoveDuration(long)}
 * </P>
 *
 * @author anotherMe17
 * @version 1.0.0
 */
public class ItemAnimator17 extends BaseItemAnimator {

    private static final long DEFAULT_ADD_DURATION = 600;
    private static final long DEFAULT_MOVE_DURATION = 600;
    private static final long DEFAULT_REMOVE_DURATION = 6000;
    private static final long DEFAULT_CHANGE_DURATION = 600;

    public ItemAnimator17() {
        this(DEFAULT_ADD_DURATION, DEFAULT_MOVE_DURATION, DEFAULT_REMOVE_DURATION, DEFAULT_CHANGE_DURATION);
    }

    public ItemAnimator17(long addDuration, long moveDuration, long removeDuration, long changeDuration) {
        setAddDuration(addDuration);
        setMoveDuration(moveDuration);
        setRemoveDuration(removeDuration);
        setChangeDuration(changeDuration);
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        System.out.println("animation remove");
        resetAnimation(holder);
        mPendingRemovals.add(holder);
        return true;
    }

    @Override
    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        System.out.println("animation remove");
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mRemoveAnimations.add(holder);
        animation.setDuration(getRemoveDuration())
                .alpha(0)
                .translationX(view.getWidth())
                .setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        dispatchRemoveStarting(holder);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        animation.setListener(null);
                        ViewCompat.setAlpha(view, 1);
                        ViewCompat.setTranslationX(view, view.getWidth());
                        dispatchRemoveFinished(holder);
                        mRemoveAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        /*System.out.println("animate move fromX = " + fromX + " fromY = " + fromY + " toX = " + toX + " toY = " + toY);*/
        final View view = holder.itemView;
        fromX += ViewCompat.getTranslationX(holder.itemView);
        fromY += ViewCompat.getTranslationY(holder.itemView);
        resetAnimation(holder);
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            ViewCompat.setTranslationX(view, -deltaX);
        }
        if (deltaY != 0) {
            ViewCompat.setTranslationY(view, -deltaY);
        }
        System.out.println("deltaX = " + deltaX + " deltaY = " + deltaY);
        mPendingMoves.add(new MoveInfo(holder, fromX, fromY, toX, toY));
        return true;
    }

    @Override
    protected void animateMoveImpl(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        System.out.println("animate move fromX = " + fromX + " fromY = " + fromY + " toX = " + toX + " toY = " + toY);
        final View view = holder.itemView;
        final int deltaX = toX - fromX;
        final int deltaY = toY - fromY;
        if (deltaX != 0) {
            ViewCompat.animate(view).translationX(0);
        }
        if (deltaY != 0) {
            ViewCompat.animate(view).translationY(0);
        }
        // TODO: make EndActions end listeners instead, since end actions aren't called when
        // vpas are canceled (and can't end them. why?)
        // need listener functionality in VPACompat for this. Ick.
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mMoveAnimations.add(holder);
        animation.setDuration(getMoveDuration()).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                dispatchMoveStarting(holder);
            }

            @Override
            public void onAnimationCancel(View view) {
                if (deltaX != 0) {
                    ViewCompat.setTranslationX(view, 0);
                }
                if (deltaY != 0) {
                    ViewCompat.setTranslationY(view, 0);
                }
            }

            @Override
            public void onAnimationEnd(View view) {
                animation.setListener(null);
                dispatchMoveFinished(holder);
                mMoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        System.out.println("animateChange ====================== ");
        if (oldHolder == newHolder) {
            // Don't know how to run change animations when the same view holder is re-used.
            // run a move animation to handle position changes.
            return animateMove(oldHolder, fromX, fromY, toX, toY);
        }
        final float prevTranslationX = ViewCompat.getTranslationX(oldHolder.itemView);
        final float prevTranslationY = ViewCompat.getTranslationY(oldHolder.itemView);
        final float prevAlpha = ViewCompat.getAlpha(oldHolder.itemView);
        resetAnimation(oldHolder);
        int deltaX = (int) (toX - fromX - prevTranslationX);
        int deltaY = (int) (toY - fromY - prevTranslationY);
        // recover prev translation state after ending animation
        ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX);
        ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY);
        ViewCompat.setAlpha(oldHolder.itemView, prevAlpha);
        if (newHolder != null) {
            // carry over translation values
            resetAnimation(newHolder);
            ViewCompat.setTranslationX(newHolder.itemView, -deltaX);
            ViewCompat.setTranslationY(newHolder.itemView, -deltaY);
            ViewCompat.setAlpha(newHolder.itemView, 0);
        }
        mPendingChanges.add(new ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
        return true;
    }

    @Override
    protected void animateChangeImpl(final ChangeInfo changeInfo) {
        System.out.println("animation change = " + changeInfo.toString());
        final RecyclerView.ViewHolder holder = changeInfo.oldHolder;
        final View view = holder == null ? null : holder.itemView;
        final RecyclerView.ViewHolder newHolder = changeInfo.newHolder;
        final View newView = newHolder != null ? newHolder.itemView : null;
        if (view != null) {
            final ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(view).setDuration(
                    getChangeDuration());
            mChangeAnimations.add(changeInfo.oldHolder);
            oldViewAnim.translationX(changeInfo.toX - changeInfo.fromX);
            oldViewAnim.translationY(changeInfo.toY - changeInfo.fromY);
            oldViewAnim.alpha(0).setListener(new VpaListenerAdapter() {
                @Override
                public void onAnimationStart(View view) {
                    dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                @Override
                public void onAnimationEnd(View view) {
                    oldViewAnim.setListener(null);
                    ViewCompat.setAlpha(view, 1);
                    ViewCompat.setTranslationX(view, 0);
                    ViewCompat.setTranslationY(view, 0);
                    dispatchChangeFinished(changeInfo.oldHolder, true);
                    mChangeAnimations.remove(changeInfo.oldHolder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        }
        if (newView != null) {
            final ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
            mChangeAnimations.add(changeInfo.newHolder);
            newViewAnimation.translationX(0).translationY(0).setDuration(getChangeDuration()).
                    alpha(1).setListener(new VpaListenerAdapter() {
                @Override
                public void onAnimationStart(View view) {
                    dispatchChangeStarting(changeInfo.newHolder, false);
                }

                @Override
                public void onAnimationEnd(View view) {
                    newViewAnimation.setListener(null);
                    ViewCompat.setAlpha(newView, 1);
                    ViewCompat.setTranslationX(newView, 0);
                    ViewCompat.setTranslationY(newView, 0);
                    dispatchChangeFinished(changeInfo.newHolder, false);
                    mChangeAnimations.remove(changeInfo.newHolder);
                    dispatchFinishedWhenDone();
                }
            }).start();
        }
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        System.out.println("animateAdd");
        resetAnimation(holder);
        //ViewCompat.setAlpha(holder.itemView, 0.5f);
        ViewCompat.setTranslationX(holder.itemView, 1440);
        mPendingAdditions.add(holder);
        return true;
    }

    @Override
    protected void animationAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mAddAnimations.add(holder);
        System.out.println("add animation duration = " + getAddDuration());
        animation
                .translationX(0)
                //.alpha(1)
                .setDuration(getAddDuration())
                .setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        System.out.println("add animation start");
                        dispatchAddStarting(holder);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        System.out.println("add animation cancel");
                        ViewCompat.setAlpha(view, 1);
                        ViewCompat.setTranslationX(view, 0);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        System.out.println("add animation end");
                        animation.setListener(null);
                        ViewCompat.setTranslationX(view, 0);
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }
}
