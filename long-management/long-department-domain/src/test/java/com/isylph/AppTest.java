package com.isylph;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest
{

    public int[] searchRange(int[] nums, int target) {
        int l = search(nums, target);
        int r = search(nums, target + 1);
        return l == r ? new int[] {-1, -1} : new int[] {l, r - 1};
    }

    private int search(int[] nums, int x) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= x) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    private int x[] = {1,2,3,8, 8, 8, 8, 8, 8, 8, 9};
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {



    }
}
